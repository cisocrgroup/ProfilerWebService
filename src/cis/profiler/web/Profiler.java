package cis.profiler.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

class Profiler {
        private static Pattern ITERATIONS = Pattern.compile("Iteration \\d+");
        private final int BUFFER_SIZE = 8192;
        private File exe, infile, profileout, docout;
        private Process process = null;
        private ProfilerInputFile in;
        private ArrayList<String> args;
        private final static Logger logger = Logger.getLogger(Profiler.class.getName());

        public Profiler(Backend backend, ProfilerInputFile in)
                throws BackendException, IOException {
                exe = backend.getProfilerExe();
                infile = makeTmpFile("profiler_input_", in.getExtension());
                docout = makeTmpFile("profiler_docout_", ".xml");
                profileout = makeTmpFile("profiler_profileout_", ".xml");
                this.in = in;
                setupCommandArgs(backend);
        }

        public int run() {
                try {
                        logger.log(Level.INFO, "writing input file for profiler ...");
                        int n = in.writeInputFile(infile);
                        logger.log(Level.INFO, "input file size: " + n);
                        ProcessBuilder builder = new ProcessBuilder(args);
                        builder.redirectErrorStream(true);
                        logger.log(Level.INFO, "starting profiler ...");
                        process = builder.start();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream())
                                );
                        String line;
                        while ((line = reader.readLine()) != null) {
                                Matcher m = ITERATIONS.matcher(line);
                                if (m.find())
                                        logger.log(Level.INFO, "line: " + line);
                        }
                        int res = process.waitFor();
                        if (res == 0)
                                compress();
                        return res;
                } catch (IOException|InterruptedException e) {
                        logger.log(Level.SEVERE, e.getMessage());
                        return 1;
                }
        }
        public void abort() {
                process.destroy();
                infile.delete();
                docout.delete();
                profileout.delete();
        }
        public File getDocOutFile() {
                return docout;
        }
        public File getProfileOutFile() {
                return profileout;
        }
        public File getInputFile() {
                return infile;
        }
        public String getCommand() {
                StringBuilder builder = new StringBuilder();
                for (String arg: args) {
                        builder.append(arg);
                        builder.append(' ');
                }
                return builder.toString();
        }

        private void setupCommandArgs(Backend backend) throws IOException, BackendException {
                args = new ArrayList<String>();
                args.add(exe.getCanonicalPath());
                args.add("--config");
                args.add(backend.getConfiguration(in.getLanguage()).getCanonicalPath());
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

        private void compress() throws IOException {
                logger.log(Level.INFO, "compressing ...");
                compressDocOut();
                compressProfileOut();
                logger.log(Level.INFO, "done compressing");
        }
        private void compressDocOut() throws IOException {
                File compressedDocout = new File(
                        docout.getCanonicalPath() + ".gz"
                        );
                logger.log(Level.INFO, "compressing " + docout + " to " + compressedDocout);
                compressFromTo(docout, compressedDocout);
                docout.delete();
                docout = compressedDocout;
        }
        private void compressProfileOut() throws IOException {
                File compressedProfileout = new File(
                        profileout.getCanonicalPath() + ".gz"
                        );
                logger.log(Level.INFO, "compressing " + profileout + " to " + compressedProfileout);
                compressFromTo(profileout, compressedProfileout);
                profileout.delete();
                profileout = compressedProfileout;

        }
        private void compressFromTo(File from, File to) throws IOException {
                final GZIPOutputStream gzipOut = new GZIPOutputStream(
                        new FileOutputStream(to)
                        );
                Files.copy(from.toPath(), gzipOut);
                gzipOut.close();
        }
}
