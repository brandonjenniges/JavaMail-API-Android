package com.brandonjenniges.javamailapidemo;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by brandonjenniges on 11/6/15.
 */
public class Mail extends javax.mail.Authenticator {
    private String _user;
    private String _pass;

    private String[] _to;
    private String _from;

    private String[] _reply_to;
    
    private String _port;
    private String _sport;

    private String _host;

    private String _subject;
    private String _body;

    private boolean _auth;

    private boolean _debuggable;

    private Multipart _multipart;

    public Mail() {
        _host = "smtp.gmail.com"; // default smtp server
        _port = "465"; // default smtp port
        _sport = "465"; // default socketfactory port

        _user = ""; // username
        _pass = ""; // password
        _from = ""; // email sent from
        _subject = ""; // email subject
        _body = ""; // email body

        _debuggable = false; // debug mode on or off - default off
        _auth = true; // smtp authentication - default on

        _multipart = new MimeMultipart();

        // There is something wrong with MailCap, javamail can not find a
        // handler for the multipart/mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap
                .getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    public Mail(String user, String pass) {
        this();

        _user = user;
        _pass = pass;
    }

    public boolean send() throws Exception {
        Properties props = _setProperties();

        if (!_user.equals("") && !_pass.equals("") && _to.length > 0
                && !_from.equals("") && !_subject.equals("")
                && !_body.equals("")) {
            Session session = Session.getInstance(props, this);

            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(_from));

            InternetAddress[] addressTo = new InternetAddress[_to.length];
            for (int i = 0; i < _to.length; i++) {
                addressTo[i] = new InternetAddress(_to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(_subject);
            msg.setSentDate(new Date());

            InternetAddress[] replyTo = new InternetAddress[_reply_to.length];
            for (int i = 0; i < _reply_to.length; i++) {
                replyTo[i] = new InternetAddress(_reply_to[i]);
            }
            if (_reply_to.length >0){
                msg.setReplyTo(replyTo);
            }
            
            // setup message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(_body);
            _multipart.addBodyPart(messageBodyPart);

            msg.setHeader("X-Priority", "1");
            // Put parts in message
            msg.setContent(_multipart);

            // send email
            Transport.send(msg);

            return true;
        } else {
            return false;
        }
    }

    public void addAttachment(String filename) throws Exception {
        BodyPart messageBodyPart = new MimeBodyPart();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        _multipart.addBodyPart(messageBodyPart);
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(_user, _pass);
    }

    private Properties _setProperties() {
        Properties props = new Properties();

        props.put("mail.smtp.host", _host);

        if (_debuggable) {
            props.put("mail.debug", "true");
        }

        if (_auth) {
            props.put("mail.smtp.auth", "true");
        }

        props.put("mail.smtp.port", _port);
        props.put("mail.smtp.socketFactory.port", _sport);
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        return props;
    }

    // the getters and setters
    public String getBody() {
        return _body;
    }

    public void setBody(String _body) {
        this._body = _body;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

    public String[] get_to() {
        return _to;
    }

    public void set_to(String[] _to) {
        this._to = _to;
    }

    public String get_from() {
        return _from;
    }

    public void set_from(String _from) {
        this._from = _from;
    }

    public String get_port() {
        return _port;
    }

    public void set_port(String _port) {
        this._port = _port;
    }

    public String get_sport() {
        return _sport;
    }

    public void set_sport(String _sport) {
        this._sport = _sport;
    }

    public String get_host() {
        return _host;
    }

    public void set_host(String _host) {
        this._host = _host;
    }

    public String get_subject() {
        return _subject;
    }

    public void set_subject(String _subject) {
        this._subject = _subject;
    }

    public boolean is_auth() {
        return _auth;
    }

    public void set_auth(boolean _auth) {
        this._auth = _auth;
    }

    public boolean is_debuggable() {
        return _debuggable;
    }

    public void set_debuggable(boolean _debuggable) {
        this._debuggable = _debuggable;
    }

    public Multipart get_multipart() {
        return _multipart;
    }

    public void set_multipart(Multipart _multipart) {
        this._multipart = _multipart;
    }
    
    public String[] get_reply_to() {
        return _reply_to;
    }

    public void set_reply_to(String[] _reply_to) {
        this._reply_to = _reply_to;
    }    
}
