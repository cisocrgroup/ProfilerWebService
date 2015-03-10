package cis.profiler.web;

import cis.profiler.mail.MailNotification;
import de.uni_muenchen.cis.www.profiler.*;
import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.wsdl.WSDLConstants;
import org.w3.www._2005._05.xmlmime.Base64Binary;

/**
 *
 * @author thorsten (thorsten.vobl@googlemail.com)
 */
public class ProfilerWebService implements cis.profiler.web.ProfilerWebServiceSkeletonInterface {

    private Connection con = null;
    private Properties prop = null;
    private Properties mailprop = null;
    private Process p;
    private BufferedWriter o;
    private int bytesRead;
    private int totalBytesRead;
    private long totalBytesToRead;
    private String status = "";
    private String info = "";
    private String ktokens = "";
    private boolean isProfiling = false;
    private boolean isUploading = false;
    private File tempfile;
    private File doc_out;
    private File profile_out;
    private File log;
    private static final String STATUS = "status";
    private static final String INFO = "info";
    private static final String KTOKENS = "ktokens";
    private static final String BYTESR = "totalbytesread";
    private static final String BYTESTR = "totalbytestoread";

    public ProfilerWebService() {
        try {
            mailprop = new Properties();
            mailprop.put("mail.smtp.port", "25");
            mailprop.put("mail.smtp.socketFactory.fallback", "false");
            mailprop.put("mail.smtp.socketFactory.class", "cis.profiler.web.DummySSLSocketFactory");
            mailprop.put("mail.smtp.quitwait", "false");
            mailprop.put("mail.smtp.host", "smtp.gmail.com");
            mailprop.put("mail.smtp.auth", "true");
            mailprop.put("mail.smtp.ssl.enable", "true");
            mailprop.put("mail.smtp.starttls.enable", "true");

            mailprop.put("gmail.username", "cis.profiler@gmail.com");
            mailprop.put("gmail.password", "upe7rub8");

            prop = new Properties();
            log = File.createTempFile("log", ".txt");
            o = new BufferedWriter(new FileWriter(log));
            log("Profiler Class created");
            prop.load(new InputStreamReader(this.getClass().getResourceAsStream("/conf/profiler.ini")));

            String dbURL = prop.getProperty("dbURL");
            String username = prop.getProperty("username");
            String password = prop.getProperty("password");
            log("dbUrl " + dbURL);
            log("username " + username);
            log ("password " + password);

            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbURL, username, password);

        } catch (IOException ex) {
            log(ex.toString());
        } catch (SQLException ex) {
            log(ex.toString());
        } catch (ClassNotFoundException ex) {
            log(ex.toString());
        } catch (RuntimeException e) {
            log(e.toString());
            throw e;
        }
    }

    private void log(String msg) {
        try {
            if (o != null) {
                o.write("[" + getDateTime() + "] ");
                o.write(msg + "\n");
                o.flush();
            }
        } catch (IOException e) {
        }
        Logger.getLogger(ProfilerWebService.class.getName()).log(Level.INFO, msg);
    }
    
    private void log(Exception e) {
         try {
            if (o != null) {
                o.write("[" + getDateTime() + "] ");
                o.write("ERROR " + e.toString() + "\n");
                o.flush();
            }
        } catch (IOException ex) {
        }
        Logger.getLogger(ProfilerWebService.class.getName()).log(Level.SEVERE, null, e);
    }
    
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    private Connection connect() throws ClassNotFoundException, SQLException {
        String dbUrl = prop.getProperty("dbURL");
        String username = prop.getProperty("username");
        String password = prop.getProperty("password");
        log("Connecting to " + dbUrl + " " + username + " " + password);

        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(dbUrl, username, password);
    }
    
    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            log(e);
        }
    }
    
    
    private void insertTransactionInfo(Connection c, String userid, String ip, String docinfo, String profname, String profinfo) throws SQLException {
        Statement s = c.createStatement();
        String sqlstr = "INSERT INTO transactions (userid, ipaddress, docname, docinfos, profname, profilerinfos) VALUES (\"" +
            userid + "\", \"" + ip + "\", \"" + getDocname() + "\", \"" + docinfo + "\", \"" + profname + "\", \"" + profinfo + "\")";
        log("sqlstr: " + sqlstr);
        s.executeUpdate(sqlstr);
    }
    
    private String getDocname() {
        return tempfile.getName() + " # " + doc_out.getName() + " # " + log.getName();
    }
    
    @Override
    public ResendIDResponse resendID(ResendIDRequest resendIDRequest) {
        ResendIDResponse resp = new ResendIDResponse();
        ResendIDResponseType rst = new ResendIDResponseType();

        ResendIDRequestType rqt = resendIDRequest.getResendIDRequest();

        if (con != null && prop != null) {
            try {
                // check if email adress has already been registered for an account
                Statement stat = con.createStatement();
                if (stat.execute("SELECT COUNT(email) FROM users WHERE email=\"" + rqt.getEmail() + "\"")) {
                    ResultSet rs = stat.getResultSet();
                    if (rs.next()) {
                        int val = rs.getInt(1);

                        // email not registered, abort and send message saying so
                        if (val == 0) {
                            rst.setMessage("email_not_registered");
                            rst.setReturncode(-1);

                            resp.setResendIDResponse(rst);
                            return resp;

                            // generate new uuid for email adress and send confirmation mail
                        } else {

                            String old_id = "";
                            stat.execute("SELECT userid FROM users WHERE email=\"" + rqt.getEmail() + "\"");
                            rs = stat.getResultSet();
                            if (rs.next()) {
                                old_id = rs.getString(1);
                            }

                            boolean unique = false;

                            while (!unique) {
                                UUID userid = UUID.randomUUID();
                                // check if the uuid is already in use for an account
                                if (stat.execute("SELECT COUNT(userid) FROM users WHERE userid=\"" + userid + "\"")) {
                                    rs = stat.getResultSet();
                                    if (rs.next()) {
                                        val = rs.getInt(1);

                                        // unique id generated, excute insert statement
                                        if (val == 0) {
                                            unique = true;

                                            int rows = stat.executeUpdate("UPDATE users SET userid=\"" + userid + "\" WHERE email=\"" + rqt.getEmail() + "\"");

                                            // update the transactions table to keep track of the users transactions
                                            stat.executeUpdate("UPDATE transactions SET userid=\"" + userid + "\" WHERE userid =\"" + old_id + "\"");

                                            // success, send email containing generated id to email adress provided
                                            if (rows > 0) {

                                                this.sendMail(rqt.getEmail(), "Your new CIS Profiler ID", "A new userid has been generated for you!\n Please copy it from this email and enter it in the GUI Options.\n\n Your ID is: " + userid);

                                                rst.setMessage("success");
                                                rst.setReturncode(0);
                                                resp.setResendIDResponse(rst);
                                                return resp;

                                            } else {
                                                rst.setMessage("database_error");
                                                rst.setReturncode(-2);

                                                resp.setResendIDResponse(rst);
                                                return resp;

                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                }

            } catch (Exception ex) {
                rst.setMessage("" + ex.getMessage());
                rst.setReturncode(-1);

                resp.setResendIDResponse(rst);
                return resp;

            }
        }
        rst.setMessage("general_error");
        rst.setReturncode(-1);

        resp.setResendIDResponse(rst);
        return resp;
    }

    @Override
    public GetConfigurationsResponse getConfigurations() {
        GetConfigurationsResponse resp = new GetConfigurationsResponse();
        GetConfigurationsResponseType rst = new GetConfigurationsResponseType();
        if (prop != null) {
            ArrayList<String> temp = new ArrayList<String>();
            File f = new File(prop.getProperty("backend_home") + "/OCRC_dictionaries/config_profiler/");
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File t = files[i];
                if (t.getName().endsWith(".ini")) {
                    temp.add(t.getName().substring(0, t.getName().indexOf(".")));
                }
            }

            String[] retval = new String[temp.size()];

            if (retval.length > 0) {
                rst.setConfigurations(temp.toArray(retval));
            } else {
                rst.setConfigurations(null);
            }
        } else {
            rst.setConfigurations(null);
        }
        resp.setGetConfigurationsResponse(rst);
        return resp;

    }

    @Override
    public GetTransactionsResponse getTransactions(GetTransactionsRequest getTransactionsRequest) {
        GetTransactionsResponse resp = new GetTransactionsResponse();
        GetTransactionsResponseType rst = new GetTransactionsResponseType();

        GetTransactionsRequestType rqt = getTransactionsRequest.getGetTransactionsRequest();

        if (con != null && prop != null) {
            try {
                Statement stat = con.createStatement();
                if (stat.execute("SELECT * FROM transactions WHERE userid=\"" + rqt.getUserid() + "\"")) {
                    ResultSet rs = stat.getResultSet();
                    ArrayList<String> temp = new ArrayList<String>();

                    if (!rs.isBeforeFirst()) {
                        rst.setTransactions(new String[0]);
                        rst.setMessage("empty");
                        rst.setReturncode(0);

                        resp.setGetTransactionsResponse(rst);
                        return resp;
                    }

                    while (rs.next()) {
                        temp.add(rs.getString(2) + "#" + rs.getString(3) + "#" + rs.getString(4) + "#" + rs.getString(5));
                    }
                    String[] retval = new String[temp.size()];
                    rst.setTransactions(temp.toArray(retval));
                    rst.setMessage("success");
                    rst.setReturncode(0);

                    resp.setGetTransactionsResponse(rst);
                    return resp;
                } else {
                    rst.setMessage("general_error");
                    rst.setReturncode(-1);
                    rst.setTransactions(new String[0]);

                    resp.setGetTransactionsResponse(rst);
                    return resp;
                }

            } catch (SQLException ex) {
                rst.setTransactions(new String[0]);
                rst.setMessage(ex.getMessage());
                rst.setReturncode(-1);

                resp.setGetTransactionsResponse(rst);
                return resp;
            }
        } else {
            rst.setMessage("general_error");
            rst.setReturncode(-1);
            rst.setTransactions(new String[0]);

            resp.setGetTransactionsResponse(rst);
            return resp;
        }
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest) {
        CreateAccountResponse resp = new CreateAccountResponse();
        CreateAccountResponseType rst = new CreateAccountResponseType();

        CreateAccountRequestType rqt = createAccountRequest.getCreateAccountRequest();

        if (con != null && prop != null) {
            try {
                // check if email adress has already been registered for an account
                Statement stat = con.createStatement();
                if (stat.execute("SELECT COUNT(email) FROM users WHERE email=\"" + rqt.getEmail() + "\"")) {
                    ResultSet rs = stat.getResultSet();
                    if (rs.next()) {
                        int val = rs.getInt(1);

                        // email already registered, return message indicating this
                        if (val == 1) {
                            rst.setMessage("email_duplicate");
                            rst.setReturncode(-1);
                            resp.setCreateAccountResponse(rst);
                            return resp;

                            // email is free to use, generate unique uuid and execute insert statement
                        } else {

                            boolean unique = false;

                            while (!unique) {
                                UUID userid = UUID.randomUUID();
                                // check if the uuid is already in use for an account
                                if (stat.execute("SELECT COUNT(userid) FROM users WHERE userid=\"" + userid + "\"")) {
                                    rs = stat.getResultSet();
                                    if (rs.next()) {
                                        val = rs.getInt(1);

                                        // unique id generated, excute insert statement
                                        if (val == 0) {
                                            unique = true;
                                            int rows = stat.executeUpdate("INSERT INTO users VALUES(\"" + rqt.getEmail() + "\",\"" + userid + "\",\"" + rqt.getUsername() + "\",\"" + rqt.getInstitution() + "\"," + Integer.parseInt(prop.getProperty("default_quota")) + ")");

                                            // success, send email containing generated id to email adress provided
                                            if (rows > 0) {

                                                try {
                                                    String[] recipients = {rqt.getEmail()};
                                                    new MailNotification().sendMail("smtp.gmail.com", "cis.profiler@gmail.com", "upe7rub8", recipients, "Welcome to CIS Profiler", "Congratulations " + rqt.getUsername() + "!\n A user account has been created for you.\n Your initial quota will be " + prop.getProperty("default_quota") + ".\n A userid has been generated for you. Please copy it from this email and enter it in the GUI Options.\n\n Your ID is: " + userid, true);
//                                                    this.sendMail(rqt.getEmail(), "Welcome to CIS Profiler", "Congratulations " + rqt.getUsername() + "!\n A user account has been created for you.\n Your initial quota will be " + prop.getProperty("default_quota") + ".\n A userid has been generated for you. Please copy it from this email and enter it in the GUI Options.\n\n Your ID is: " + userid);
                                                } catch (Exception e) {
                                                    PrintWriter writer = new PrintWriter(File.createTempFile("log", ".txt").getCanonicalPath(), "UTF-8");
                                                    e.printStackTrace(writer);
                                                    writer.flush();
                                                    writer.close();
                                                    o.write("" + e.getMessage());
                                                    o.flush();
                                                    stat.executeUpdate("DELETE FROM users WHERE email=\"" + rqt.getEmail() + "\"");
                                                    rst.setMessage("general_error: " + e.getMessage());
                                                    rst.setReturncode(-1);
                                                    resp.setCreateAccountResponse(rst);
                                                    return resp;
                                                }

                                                rst.setMessage("success");
                                                rst.setReturncode(0);

                                                resp.setCreateAccountResponse(rst);
                                                return resp;


                                            } else {
                                                rst.setMessage("database_error");
                                                rst.setReturncode(-2);

                                                resp.setCreateAccountResponse(rst);
                                                return resp;

                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                }

            } catch (Exception ex) {
                rst.setMessage("" + ex.getMessage());
                rst.setReturncode(-20);
                resp.setCreateAccountResponse(rst);
                return resp;
            }
        }
        rst.setMessage("general_error2");
        rst.setReturncode(-1);
        resp.setCreateAccountResponse(rst);
        return resp;
    }

    @Override
    public CheckQuotaResponse checkQuota(CheckQuotaRequest checkQuotaRequest) {
        CheckQuotaResponse resp = new CheckQuotaResponse();
        CheckQuotaResponseType rst = new CheckQuotaResponseType();

        CheckQuotaRequestType rqt = checkQuotaRequest.getCheckQuotaRequest();

        if (con != null && prop != null) {
            try {
                Statement stat = con.createStatement();
                if (stat.execute("SELECT quota FROM users WHERE userid=\"" + rqt.getUserid() + "\"")) {
                    ResultSet rs = stat.getResultSet();
                    if (rs.next()) {
                        rst.setQuota(rs.getInt(1));
                        rst.setMessage("success");
                        rst.setReturncode(0);
                        resp.setCheckQuotaResponse(rst);
                        return resp;
                    } else {
                        rst.setQuota(-1);
                        rst.setMessage("no_such_id");
                        rst.setReturncode(-1);
                        resp.setCheckQuotaResponse(rst);
                        return resp;
                    }
                } else {
                    rst.setQuota(-1);
                    rst.setMessage("empty");
                    rst.setReturncode(-1);
                    resp.setCheckQuotaResponse(rst);
                    return resp;
                }
            } catch (SQLException ex) {
                rst.setQuota(-1);
                rst.setMessage(ex.getMessage());
                rst.setReturncode(-1);
                resp.setCheckQuotaResponse(rst);
                return resp;
            }
        } else {
            rst.setQuota(-1);
            rst.setMessage("general_error");
            rst.setReturncode(-1);
            resp.setCheckQuotaResponse(rst);
            return resp;
        }
    }

    private static String concat(Iterable iterable, char sep) {
        Iterator it = iterable.iterator();
        StringBuilder str = new StringBuilder();
        while (it.hasNext()) {
            str.append(it.next());
            str.append(sep);
        }
        return str.toString();
    }
    
    @Override
    public GetProfileResponse getProfile(GetProfileRequest getProfileRequest) {
        GetProfileResponse resp = new GetProfileResponse();
        GetProfileResponseType rst = new GetProfileResponseType();

        GetProfileRequestType rqt = getProfileRequest.getGetProfileRequest();
        totalBytesToRead = rqt.getDoc_in_size();

        if (con != null && prop != null) {
            try {
                MessageContext outMsgCtx = MessageContext.getCurrentMessageContext().getOperationContext().getMessageContext(WSDLConstants.MESSAGE_LABEL_OUT_VALUE);
                outMsgCtx.setProperty(org.apache.axis2.Constants.Configuration.ENABLE_MTOM, org.apache.axis2.Constants.VALUE_TRUE);

                Object property = MessageContext.getCurrentMessageContext().getProperty("REMOTE_ADDR");
                String ip = property.toString();

                //insertTransactionInfo(rqt.getUserid(), ip, getDocname(), rqt.getDoc_in_size() + " # " + doc_out.length(), profile_out.getName(), "" + profile_out.length());
                // if quota == 0 no more profiles can be computed
                int quota = 0;
                Statement stat = con.createStatement();
                String sqlstr = "SELECT quota FROM users WHERE userid=\"" + rqt.getUserid() + "\"";
                log("sqlstr: " + sqlstr);
                if (stat.execute(sqlstr)) {
                    ResultSet rs = stat.getResultSet();
                    if (rs.next()) {
                        quota = rs.getInt(1);
                    }
                }

                if (quota == 0) {
                    rst.setMessage("no_quota");
                    rst.setDoc_out(new AttachmentType());
                    rst.setProfile_out(new AttachmentType());
                    rst.setQuota_left(-1);
                    rst.setDoc_out_size(-1);
                    rst.setProfile_out_size(-1);
                    rst.setReturncode(-1);
                    resp.setGetProfileResponse(rst);
                    return resp;
                }

                log("Old quota: " + quota);

                // write input data to temporary file
                DataHandler dh = rqt.getDoc_in().getBinaryData().getBase64Binary();

                String inputType = rqt.getDoc_in_type();
                if( inputType.equals(FileType.DOCXML.toString())) {
                    tempfile = File.createTempFile("doc_", ".xml");
                } else if( inputType.equals(FileType.TXT.toString())) {
                    tempfile = File.createTempFile("doc_", ".txt");                    
                } else {
                    rst.setDoc_out(new AttachmentType());
                    rst.setProfile_out(new AttachmentType());
                    rst.setDoc_out_size(-1);
                    rst.setProfile_out_size(-1);
                    rst.setQuota_left(-1);
                    rst.setReturncode(-1);
                    rst.setMessage("unknown inputtype");
                    rst.setReturncode(-1);
                    resp.setGetProfileResponse(rst);
                    return resp;                    
                }
                
                FileOutputStream out = new FileOutputStream(tempfile);
                InputStream in = dh.getInputStream();

                byte[] buffer = new byte[8192];
                bytesRead = 0;

                status = "uploading";

                this.isUploading = true;
                while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                    totalBytesRead += bytesRead;
                    out.write(buffer, 0, bytesRead);
                }

                out.flush();
                out.close();
                this.isUploading = false;

                // create output files
                profile_out = File.createTempFile("profile_", ".xml");
                doc_out = File.createTempFile("docout_", ".ocrcxml");

                Vector<String> command = new Vector<String>();
                command.add(prop.getProperty("backend_home") + "/OCRC_cxx/build/bin/profiler");
                command.add("--sourceFormat");

                if (inputType.equals(FileType.ABBYY_XML_DIR.toString())) {
                    command.add("ABBYY_XML_DIR");
                } else if (inputType.equals(FileType.DOCXML.toString())) {
                    command.add("DocXML");
                } else if (inputType.equals(FileType.TXT.toString())) {
                    command.add("TXT");
                } else {
                    rst.setDoc_out(new AttachmentType());
                    rst.setProfile_out(new AttachmentType());
                    rst.setDoc_out_size(-1);
                    rst.setProfile_out_size(-1);
                    rst.setQuota_left(-1);
                    rst.setReturncode(-1);
                    rst.setMessage("unknown inputtype");
                    rst.setReturncode(-1);
                    resp.setGetProfileResponse(rst);
                    return resp;
                }
                command.add("--config");
                command.add(prop.getProperty("backend_home") + "/OCRC_dictionaries/config_profiler/" + rqt.getConfiguration() + ".ini");
                command.add("--sourceFile");
                command.add(tempfile.getCanonicalPath());
                command.add("--out_doc");
                command.add(doc_out.getCanonicalPath());
                command.add("--out_xml");
                command.add(profile_out.getCanonicalPath());
                //log(command.toString());

                ProcessBuilder b = new ProcessBuilder(command);
                log("Command: " + concat(command, ' '));
                b.redirectErrorStream(true);

                status = "profiling";

                p = b.start();
                
                insertTransactionInfo(con, rqt.getUserid(), ip, "", "", "");
                
                log("profiling started");
                //o.write("profiling started " + System.currentTimeMillis() + "\n");
                //o.flush();

                this.isProfiling = true;
                String line = "";
                String maxiterations = "";

                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = br.readLine()) != null) {
                    // search the max iterations
                    if (line.contains("number of iterations")) {
                        Pattern pattern = Pattern.compile("([0-9]+)");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            maxiterations = matcher.group(1);
                        }
                    } else if (line.contains("*** Iteration")) {
                        Pattern pattern = Pattern.compile("\\*\\*\\* Iteration ([0-9]+) \\*\\*\\*");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            info = "Iteration " + matcher.group(1) + "/" + maxiterations;
                        }
                    } else if (line.contains("tokens processed")) {
                        Pattern pattern = Pattern.compile("([0-9]+k)");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            ktokens = matcher.group(1);
                        }
                    }
                }

//                p.wait();
                log("profiling ended");
                //o.write("profiling ended " + System.currentTimeMillis() + "\n");
                //o.flush();

                this.isProfiling = false;
//                int retval = p.exitValue();
//                                
//                o.write("Profiling done, retval= "+ retval);
//                o.flush();
//
//                if (retval != 0) {
//                    rst.setMessage("general_error");
//                    rst.setReturncode(-1);
//                    rst.setDoc_out_size(-1);
//                    rst.setProfile_out_size(-1);
//                    rst.setDoc_out(new AttachmentType());
//                    rst.setProfile_out(new AttachmentType());
//                    rst.setQuota_left(-1);
//
//                    resp.setGetProfileResponse(rst);
//                    return resp;
//                }

                // update database

                quota -= 1;
                sqlstr = "UPDATE users SET quota=" + quota + " WHERE userid=\"" + rqt.getUserid() + "\"";
                log("New quota: " + quota);
                log("sqlstr: " + sqlstr);
                stat.executeUpdate(sqlstr);

                insertTransactionInfo(con, rqt.getUserid(), ip, rqt.getDoc_in_size() + " # " + doc_out.length(), profile_out.getName(), "" + profile_out.length());
                //stat.executeUpdate("INSERT INTO transactions (userid, ipaddress, docname, docinfos, profname, profilerinfos) VALUES (\"" + rqt.getUserid() + "\",\"" + ip + "\",\"" + tempfile.getName() + " # " + doc_out.getName() + "\",\"" + rqt.getDoc_in_size() + " # " + doc_out.length() + "\",\"" + profile_out.getName() + "\",\"" + profile_out.length() + "\")");

                log("After database update");

                // build response
                AttachmentType docoutatt = new AttachmentType();
                AttachmentType profileoutatt = new AttachmentType();

                Base64Binary docoutbin = new Base64Binary();
                Base64Binary profileoutbin = new Base64Binary();

                FileDataSource docoutds = new FileDataSource(doc_out);
                FileDataSource profileoutds = new FileDataSource(profile_out);

                DataHandler docoutdh = new DataHandler(docoutds);
                DataHandler profileoutdh = new DataHandler(profileoutds);

                docoutbin.setBase64Binary(docoutdh);
                profileoutbin.setBase64Binary(profileoutdh);

                docoutatt.setBinaryData(docoutbin);
                profileoutatt.setBinaryData(profileoutbin);

                rst.setDoc_out_size(doc_out.length());
                rst.setDoc_out(docoutatt);
                rst.setProfile_out_size(profile_out.length());
                rst.setProfile_out(profileoutatt);
                rst.setQuota_left(quota);
                rst.setMessage("success");
                rst.setReturncode(0);

                resp.setGetProfileResponse(rst);
                return resp;

            } catch (Exception ex) {
                log(ex);
                rst.setDoc_out_size(-1);
                rst.setProfile_out_size(-1);
                rst.setMessage("#" + ex.getMessage());
                rst.setReturncode(-1);
                rst.setDoc_out(new AttachmentType());
                rst.setProfile_out(new AttachmentType());
                rst.setQuota_left(-1);
                resp.setGetProfileResponse(rst);
                return resp;
            }

        } else {
            rst.setDoc_out(new AttachmentType());
            rst.setProfile_out(new AttachmentType());
            rst.setDoc_out_size(-1);
            rst.setProfile_out_size(-1);
            rst.setQuota_left(-1);
            rst.setReturncode(-1);
            rst.setMessage("general_error");
            rst.setReturncode(-1);
            resp.setGetProfileResponse(rst);
            return resp;
        }
    }

    private void sendMail(String tomail, String subj, String txt) throws Exception {

        Session session = Session.getInstance(mailprop);

        String username = (String) mailprop.get("gmail.username");
        String password = (String) mailprop.get("gmail.password");

        Message msg = new MimeMessage(session);

        msg.setSubject(subj);

        InternetAddress from = new InternetAddress("cis.profiler@gmail.com", "CIS Profiler");
        InternetAddress to = new InternetAddress(tomail);
        msg.addRecipient(Message.RecipientType.TO, to);

        msg.setFrom(from);

        // set reply to email address
        InternetAddress[] replyToAddress = new InternetAddress[1];
        replyToAddress[0] = new InternetAddress("cis.profiler@gmail.com");
        msg.setReplyTo(replyToAddress);

        Multipart multipart = new MimeMultipart("related");

        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(txt, "text/plain");

        multipart.addBodyPart(htmlPart);
        msg.setContent(multipart);

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", 465, username, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }

    @Override
    public GetProfilingStatusResponse getProfilingStatus(GetProfilingStatusRequest getProfilingStatusRequest) {
        GetProfilingStatusResponse resp = new GetProfilingStatusResponse();
        GetProfilingStatusResponseType respt = new GetProfilingStatusResponseType();

        if (this.isProfiling) {
            respt.setReturncode(0);
            respt.setMessage("success");
            respt.setStatus(this.status);
            respt.setAdditional("" + this.info + " " + ktokens);
        } else if (this.isUploading) {
            respt.setReturncode(0);
            respt.setMessage("success");
            respt.setStatus(this.status);
            respt.setAdditional("" + this.totalBytesRead + "/" + this.totalBytesToRead);
        } else {
            respt.setReturncode(-1);
            respt.setMessage("not_profiling");
            respt.setStatus("not_profiling");
            respt.setAdditional("not_profiling");
        }

        resp.setGetProfilingStatusResponse(respt);
        return resp;
    }

    @Override
    public ValidateEmailResponse validateEmail(ValidateEmailRequest validateEmailRequest) {
        ValidateEmailRequestType rqt = validateEmailRequest.getValidateEmailRequest();


        ValidateEmailResponse resp = new ValidateEmailResponse();
        ValidateEmailResponseType rst = new ValidateEmailResponseType();


        Statement stat;
        try {
            stat = con.createStatement();
            if (stat.execute("SELECT COUNT(email) FROM users WHERE email=\"" + rqt.getEmail() + "\"")) {
                ResultSet rs = stat.getResultSet();
                if (rs.next()) {
                    int val = rs.getInt(1);

                    // email already registered, return message indicating this
                    if (val == 1) {
                        rst.setMessage("email_duplicate");
                        rst.setIsvalid(false);
                        rst.setReturncode(0);

                        resp.setValidateEmailResponse(rst);
                        return resp;

                        // email is free to use, generate unique uuid and execute insert statement
                    } else {
                        rst.setMessage("valid_email");
                        rst.setReturncode(0);
                        rst.setIsvalid(true);

                        resp.setValidateEmailResponse(rst);
                        return resp;
                    }
                }
            }
        } catch (SQLException ex) {
        }
        rst.setMessage("general_error");
        rst.setIsvalid(false);
        rst.setReturncode(-1);

        resp.setValidateEmailResponse(rst);
        return resp;

    }

    @Override
    public AbortProfilingResponse abortProfiling(AbortProfilingRequest abortProfilingRequest) {
        AbortProfilingResponse resp = new AbortProfilingResponse();
        AbortProfilingResponseType respt = new AbortProfilingResponseType();

        if (this.isProfiling) {
            p.destroy();
            doc_out.deleteOnExit();
            profile_out.deleteOnExit();
            tempfile.deleteOnExit();
            respt.setMessage("destroyed");
            respt.setReturncode(0);
        } else {
            respt.setMessage("not_profiling");
            respt.setReturncode(0);
        }

        resp.setAbortProfilingResponse(respt);
        return resp;
    }

    @Override
    public SimpleEnrichResponse simpleEnrich(SimpleEnrichRequest simpleEnrichRequest) {

        SimpleEnrichResponse resp = new SimpleEnrichResponse();
        SimpleEnrichResponseType rst = new SimpleEnrichResponseType();

        SimpleEnrichRequestType rqt = simpleEnrichRequest.getSimpleEnrichRequest();
        totalBytesToRead = rqt.getDoc_in_size();

        if (con != null && prop != null) {
            try {
                o.write("SimpleEnrich");
                o.flush();

                MessageContext outMsgCtx = MessageContext.getCurrentMessageContext().getOperationContext().getMessageContext(WSDLConstants.MESSAGE_LABEL_OUT_VALUE);
                outMsgCtx.setProperty(org.apache.axis2.Constants.Configuration.ENABLE_MTOM, org.apache.axis2.Constants.VALUE_TRUE);

                Object property = MessageContext.getCurrentMessageContext().getProperty("REMOTE_ADDR");
                String ip = property.toString();

                // write input data to temporary file
                DataHandler dh = rqt.getDoc_in().getBinaryData().getBase64Binary();
                tempfile = File.createTempFile("doc_", ".xml");
                FileOutputStream out = new FileOutputStream(tempfile);
                InputStream in = dh.getInputStream();

                byte[] buffer = new byte[8192];
                bytesRead = 0;

                status = "uploading";
                this.isUploading = true;
                while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                    totalBytesRead += bytesRead;
                    out.write(buffer, 0, bytesRead);
                }

                out.flush();
                out.close();
                this.isUploading = false;

                // create output files
                doc_out = File.createTempFile("docout_", ".ocrcxml");

                Vector<String> command = new Vector<String>();
                command.add(prop.getProperty("backend_home") + "/OCRC_cxx/build/bin/simpleEnrich");
                command.add("--config");
                command.add(prop.getProperty("backend_home") + "/OCRC_dictionaries/config_gui/" + rqt.getConfiguration() + ".ini");
                command.add("--out");
                command.add(doc_out.getCanonicalPath());
                command.add(tempfile.getCanonicalPath());

                ProcessBuilder b = new ProcessBuilder(command);
                b.redirectErrorStream(true);

                o.write(command.toString());
                o.flush();

                status = "profiling";


                p = b.start();

                o.write("profiling started " + System.currentTimeMillis() + "\n");
                o.flush();

                this.isProfiling = true;
                String line = "";

                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = br.readLine()) != null) {
                    // search the max iterations
                    if (line.contains("Page")) {
                        Pattern pattern = Pattern.compile("([0-9]+)");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            info = "Page " + matcher.group(1);
                        }
                    } else if (line.contains("Analyzed")) {
                        Pattern pattern = Pattern.compile("([0-9]+k)");
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            ktokens = matcher.group(1);
                        }
                    }
                }

                o.write("profiling ended " + System.currentTimeMillis() + "\n");
                o.flush();

                this.isProfiling = false;

                Statement stat = con.createStatement();
                stat.executeUpdate("INSERT INTO simpletransactions ( ipaddress, docname, docinfos ) VALUES (\"" + ip + "\",\"" + tempfile.getName() + " # " + doc_out.getName() + "\",\"" + rqt.getDoc_in_size() + " # " + doc_out.length() + "\")");

                o.write("After database update");
                o.flush();

                // build response
                AttachmentType docoutatt = new AttachmentType();
                Base64Binary docoutbin = new Base64Binary();
                FileDataSource docoutds = new FileDataSource(doc_out);
                DataHandler docoutdh = new DataHandler(docoutds);
                docoutbin.setBase64Binary(docoutdh);
                docoutatt.setBinaryData(docoutbin);

                rst.setDoc_out_size(doc_out.length());
                rst.setDoc_out(docoutatt);
                rst.setMessage("success");
                rst.setReturncode(0);

                resp.setSimpleEnrichResponse(rst);
                return resp;

            } catch (Exception ex) {
                try {
                    o.write(ex.getMessage());
                    o.flush();
                } catch (IOException ex1) {
                    Logger.getLogger(ProfilerWebService.class.getName()).log(Level.SEVERE, null, ex1);
                }
                rst.setDoc_out_size(-1);
                rst.setMessage("#" + ex.getMessage());
                rst.setReturncode(-1);
                rst.setDoc_out(new AttachmentType());
                return resp;
            }

        } else {
            rst.setDoc_out(new AttachmentType());
            rst.setDoc_out_size(-1);
            rst.setReturncode(-1);
            rst.setMessage("general_error");
            rst.setReturncode(-1);
            resp.setSimpleEnrichResponse(rst);
            return resp;
        }
    }

    @Override
    public GetSimpleConfigurationsResponse getSimpleConfigurations() {
        GetSimpleConfigurationsResponse resp = new GetSimpleConfigurationsResponse();
        GetSimpleConfigurationsResponseType rst = new GetSimpleConfigurationsResponseType();
        if (prop != null) {
            ArrayList<String> temp = new ArrayList<String>();
            File f = new File(prop.getProperty("backend_home") + "/OCRC_dictionaries/config_gui/");
            File[] files = f.listFiles();
            for (int i = 0; i < files.length; i++) {
                File t = files[i];
                if (t.getName().endsWith(".ini")) {
                    temp.add(t.getName().substring(0, t.getName().indexOf(".")));
                }
            }

            String[] retval = new String[temp.size()];

            if (retval.length > 0) {
                rst.setConfigurations(temp.toArray(retval));
            } else {
                rst.setConfigurations(null);
            }
        } else {
            rst.setConfigurations(null);
        }
        resp.setGetSimpleConfigurationsResponse(rst);
        return resp;
    }

    @Override
    public StartSessionResponse startSession() {
        StartSessionResponseType ssrt = new StartSessionResponseType();
        StartSessionResponse ssr = new StartSessionResponse();
        ssrt.setReturncode(0);
        ssr.setStartSessionResponse(ssrt);
        return ssr;
    }
}
