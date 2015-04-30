package cis.profiler.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class Profiler {
        private final int BUFFER_SIZE = 8192;
        private File exe, infile, profileout, docout;
        private Status status;
        private Process process = null;
        private ProfilerInputFile in;
        private ArrayList<String> args;

        public Profiler(Backend backend, ProfilerInputFile in)
                throws BackendException, IOException {
                status = new StatusNotStarted();
                exe = backend.getProfilerExe();
                infile = makeTmpFile("profiler_input_", in.getExtension());
                docout = makeTmpFile("profiler_docout_", ".xml");
                profileout = makeTmpFile("profiler_profileout_", ".xml");
                this.in = in;
                setupCommandArgs(backend);
        }

        public void run() throws IOException, InterruptedException {
                try {
                        status = new StatusUploading();
                        in.writeInputFile(infile);
                        status = new StatusProfiling();
                        ProcessBuilder builder = new ProcessBuilder(args);
                        builder.redirectErrorStream(true);
                        process = builder.start();
                        process.wait();
                        status = new StatusFinished(process.exitValue());
                } catch (IOException e) {
                        status = new StatusError(e.getMessage());
                        throw e;
                } catch (InterruptedException e) {
                        status = new StatusError(e.getMessage());
                        throw e;
                }
        }

        public File getDocoutFile() {
                return docout;
        }
        public File getProfileOutFile() {
                return profileout;
        }
        public File getInputFile() {
                return infile;
        }
        public Status getStatus() {
                return status;
        }
        public String getCommand() {
                StringBuilder builder = new StringBuilder();
                for (String arg: args) {
                        builder.append(arg);
                        builder.append(' ');
                }
                return builder.toString();
        }

        private void setupCommandArgs(Backend backend) throws IOException {
                args = new ArrayList<String>();
                args.add(exe.getCanonicalPath());
                args.add("--config");
                args.add(backend.getConfiguration(in.getLanguage()));
                args.add("--sourceFile");
                args.add(infile.getCanonicalPath());
                args.add("--sourceFormat");
                args.add(in.getProfilerInputType());
                args.add("--out_doc");
                args.add(docout.getCanonicalPath());
                args.add("--out_xml");
                args.add(profileout.getCanonicalPath());
        }

        private static File makeTmpFile(String prefix, String suffix) throws IOException {
                return File.createTempFile(prefix, suffix);
        }

        public abstract class Status {
                public boolean isOk() {
                        return true;
                }
                public abstract String getStatus();
        }
        private class StatusNotStarted extends Status {
                @Override
                public String getStatus() {
                        return "Not started";
                }
        }
        private class StatusProfiling extends Status {
                @Override
                public String getStatus() {
                        return "Profiling";
                }
        }
        private class StatusFinished extends Status {
                int status;
                public StatusFinished(int status) {
                        this.status = status;
                }
                @Override
                public boolean isOk() {
                        return status == 0;
                }
                @Override
                public String getStatus() {
                        return "Finished profiling";
                }
        }
        private class StatusUploading extends Status {
                @Override
                public String getStatus() {
                        return "Uploading";
                }
        }
        private class StatusError extends Status {
                private String error;
                public StatusError(String error) {
                        this.error = error;
                }
                @Override
                public boolean isOk() {
                        return false;
                }
                @Override
                public String getStatus() {
                        return "Error: " + error;
                }
        }
}
