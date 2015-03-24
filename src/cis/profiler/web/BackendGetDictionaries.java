package cis.profiler.web;

import java.io.File;
import java.util.Properties;
import java.util.ArrayList;

public class BackendGetDictionaries {
        private final File dir_;
        public BackendGetDictionaries(Properties props) {
                dir_ = new File(
                        props.getProperty("backend_home") +
                        "/OCRC_dictionaries/config_profiler/");
        }
        public String[] getDictionaries() {
                ArrayList<String> configs  = new ArrayList();
                for (File file: dir_.listFiles()) {
                        if (file.getName().endsWith(".ini")) {
                                configs.add(file.getName().replaceAll("\\.ini", ""));
                        }
                }
                String[] result = new String[configs.size()];
                configs.toArray(result);
                return result;
        }
}
