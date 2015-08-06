package cis.profiler.web;

import de.uni_muenchen.cis.www.profiler.GetProfileRequest;
import de.uni_muenchen.cis.www.profiler.GetProfileRequestType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.zip.GZIPInputStream;
import javax.activation.DataHandler;

/**
 * @author flo (flo@cis.lmu.de)
 */
public abstract class ProfilerInputFile {
        private static final String DOCXML = "DOCXML";
        private static final String TXT = "TXT";
        private static final int BUFFER_SIZE = 8192;
        private InputStream is;
        private String language;

        public ProfilerInputFile(InputStream is, String l) {
                this.is = is;
                language = l;
        }
        public abstract String getExtension();
        public abstract String getProfilerInputType();
        public String getLanguage() {
                return language;
        }
        public void writeInputFile(File outfile) throws IOException {
                byte[] buffer = new byte[BUFFER_SIZE];
                int total = 0, bytesRead = 0;
                Files.copy(is, outfile.toPath());
                is.close();
        }
        public static ProfilerInputFile fromRequest(GetProfileRequest r)
                throws BackendException, IOException {

                GetProfileRequestType rt = r.getGetProfileRequest();
                String fileType = rt.getDoc_in_type();
                String l = rt.getConfiguration();
                DataHandler dh = rt.getDoc_in().getBinaryData().getBase64Binary();
                InputStream is = new GZIPInputStream(dh.getInputStream());

                switch (fileType) {
                case DOCXML:
                        return new DocXmlProfilerInputFile(is, l);
                case TXT:
                        return new TxtProfilerInputFile(is, l);
                default:
                        throw new BackendException("Invalid file type: " + fileType);
                }
        }


        private static class TxtProfilerInputFile extends ProfilerInputFile {
                public TxtProfilerInputFile(InputStream is, String l) {
                        super(is, l);
                }
                @Override
                public String getExtension() {
                        return ".txt";
                }
                @Override
                public String getProfilerInputType() {
                        return "TXT";
                }
        }
        private static class DocXmlProfilerInputFile extends ProfilerInputFile {
                public DocXmlProfilerInputFile(InputStream is, String l) {
                        super(is, l);
                }
                @Override
                public String getExtension() {
                        return ".xml";
                }
                @Override
                public String getProfilerInputType() {
                        return "DocXML";
                }
        }
}
