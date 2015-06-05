package cis.profiler.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

class Backend {
        private final String BACKEND_KEY = "backend";
        private Properties backend;

        public Backend(InputStream is) throws IOException {
                backend = new Properties();
                backend.load(new InputStreamReader(is));
        }

        public File getBackendDir() throws BackendException {
                File dir = new File(mustGet(BACKEND_KEY));
                if (!dir.exists())
                        throw new BackendException(dir + " does not exist");
                else if (!dir.isDirectory())
                        throw new BackendException(dir + " is not a directory");
                assert(dir != null);
                return dir;
        }

        public File getProfilerExe() throws BackendException {
                File exe = new File(getBackendDir(), "/bin/profiler");
                if (!exe.exists()) {
                        throw new BackendException(exe + " does not exist");
                } else if (!exe.canExecute()) {
                        throw new BackendException("cannot execute " + exe);
                }
                assert(exe != null);
                return exe;
        }
        public File getConfiguration(String language) throws BackendException {
                File res = new File(getBackendDir(), language + ".ini");
                if (!res.exists())
                        throw new BackendException(
                                "invalid configuration file: " + res);
                return res;
        }
        public String[] getLanguages() throws BackendException {
                ArrayList<String> languages  = new ArrayList();
                File backendDir = getBackendDir();
                for (File file: backendDir.listFiles()) {
                        if (file.getName().endsWith(".ini")) {
                                languages.add(file.getName().replaceAll("\\.ini", ""));
                        }
                }
                String[] result = new String[languages.size()];
                languages.toArray(result);
                return result;
        }

        private String mustGet(String key) throws BackendException {
                String val = backend.getProperty(key);
                if (val == null) {
                        throw new BackendException(
                                "missing configuration value: " + key);
                }
                assert(val != null);
                return val;
        }
}
