package cis.profiler.client;

import cis.profiler.web.ProfilerWebServiceStub;
import de.uni_muenchen.cis.www.profiler.AttachmentType;
import de.uni_muenchen.cis.www.profiler.GetProfileRequest;
import de.uni_muenchen.cis.www.profiler.GetProfileRequestType;
import java.io.File;
import java.io.IOException;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import org.apache.axis2.Constants;
import org.w3.www._2005._05.xmlmime.Base64Binary;

class Main {
        public static void main(String[] args) throws Exception {
                if (args.length != 3) {
                        throw new RuntimeException("Usage: <url> <filename> <configuration>");
                }
                ProfilerWebServiceStub stub = getStub(args);
                GetProfileRequest request = getProfileRequest(getFilePath(args), getConfiguration(args));
                stub.getProfile(request);
        }

        private static final ProfilerWebServiceStub getStub(String[] args) throws Exception {
                ProfilerWebServiceStub stub = new ProfilerWebServiceStub(getUrl(args));
                stub._getServiceClient().getOptions().setManageSession(true);
                stub._getServiceClient().getOptions().setProperty(
                        Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
                stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(3600000);
                return stub;
        }

        private static final GetProfileRequest getProfileRequest(String filename, String config)
                throws IOException {
                GetProfileRequest r = new GetProfileRequest();
                GetProfileRequestType type = new GetProfileRequestType();
                File file = new File(filename);
                AttachmentType attachment = new AttachmentType();
                Base64Binary binary = new Base64Binary();
                FileDataSource source = new FileDataSource(file.getCanonicalPath());
                DataHandler handler = new DataHandler(source);
                binary.setBase64Binary(handler);
                attachment.setBinaryData(binary);
                type.setDoc_in(attachment);
                type.setDoc_in_size(file.length());
                type.setDoc_in_type("TXT");
                type.setUserid("nouser");
                type.setConfiguration(config);
                r.setGetProfileRequest(type);
                return r;
        }
        private static final String getUrl(String[] args) {
                assert(3 <= args.length);
                return args[0];
        }
        private static final String getFilePath(String[] args) {
                assert(3 <= args.length);
                return args[1];
        }
        private static final String getConfiguration(String[] args) {
                assert(3 <= args.length);
                return args[2];
        }
};
