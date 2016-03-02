package cis.profiler.web;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

class Backend {
        private final Logger logger;
        private final String BACKEND_KEY = "backend";
        private final String PROFILER_KEY = "profiler";
        private Properties backend;

        public Backend(InputStream is) throws Exception {
                logger = Logger.getLogger(Backend.class.getName());
                logger.log(Level.INFO, "logger initialized");
                backend = new Properties();
                logger.log(Level.INFO, "new properties created");
                if (is == null) {
                        throw new Exception("Could not read configuration file: null");
                }
                backend.load(is);
                logger.log(Level.INFO, "Backend loaded");
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
                File exe = new File(mustGet(PROFILER_KEY));
                if (!exe.exists())
                        throw new BackendException(exe + " does not exist");
                else if (!exe.canExecute())
                        throw new BackendException("cannot execute " + exe);
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
        public String[] getConfigurations() throws BackendException {
                File backendDir = getBackendDir();
                File[] iniFiles = backendDir.listFiles(
                        new ConfigurationsFileFilter());
                if (iniFiles == null)
                        return new String[0];
                String[] result = new String[iniFiles.length];
                for (int i = 0; i < iniFiles.length; ++i)
                        result[i] = iniFiles[i].getName().replaceAll("\\.ini", "");
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

        private class ConfigurationsFileFilter implements FileFilter {
                @Override
                public boolean accept(File file) {
                        return file != null && file.getName().endsWith(".ini");
                }
        }
}
