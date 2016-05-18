/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticClasses;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Panker
 */
public class Mail {

    public void Mail() {

    }

    public boolean sendPassword(String to, String password) {
        try {
            if (to != "") {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "false");
                props.put("mail.smtp.ssl.enable", "true");

                Session sess = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("mail.microsity@gmail.com", "156456851");
                    }
                }
                );

                Message msg = new MimeMessage(sess);
                msg.setSubject("Восстановление пароля");
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                String bodyPart = "<img src=\"http://microsity.info/resources/images/logo2.png\" style=\"width:120px\" alt=\"MicroSity\">" + "<div style=\"color:red;text-size:14px;\">Здравствуйте, это автоматическое письмо и отвечать на него не надо.</div>\n"
                        + "Ваши регистрационные данные ресурса <a hrew=\"http://microsity.info\" style=\"color:blue\"> Microsity</a>\n"
                        + "<br/><hr/>"
                        + "<div>"
                        + "<p style=\"color:green\">Эл. адрес:</p>"
                        + to
                        + "<p style=\"color:green\">Пароль:</p>"
                        + password
                        + "</div>";
                msg.setContent(bodyPart, "text/html; charset=utf-8");
                msg.setSentDate(new Date());
                Transport.send(msg);
                return true;
            }

        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {

        }
        return false;
    }

    public boolean confirmMail(String to,String pass) {
        try {
            if (to != "") {
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "false");
                props.put("mail.smtp.ssl.enable", "true");

                Session sess = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication("mail.microsity@gmail.com", "156456851");
                    }
                }
                );

                Message msg = new MimeMessage(sess);
                msg.setSubject("Успешная регистрация!");
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                String bodyPart = "<img src=\"http://microsity.info/resources/images/logo2.png\" style=\"width:120px\" alt=\"Google logo\">\n"
                        + "\n"
                        + "<h2>Добро пожаловать!</h2>\n"
                        + "<h3>Вы успешно зарегистрировались в MicroSity.info</h3>\n"
                        + "\n"
                        + "<h4>Ваш пароль :</h4> <p style=\"color: green\">"
                        +pass
                        + "</p>\n"
                        + "<h4><p style=\"color:red\">Пожалуйста, никому не сообщайте Ваш пароль. Администрации портала он не нужен! </p></h4>\n"
                        + "\n"
                        + "Форма подтверждения почтового ящика в процессе изготовления!";
                msg.setContent(bodyPart, "text/html; charset=utf-8");
                msg.setSentDate(new Date());
                Transport.send(msg);
            }

        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return false;
    }

    public void test() {

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.ssl.enable", "true");

            Session sess = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication("mail.microsity@gmail.com", "156456851");
                }
            }
            );

            Message msg = new MimeMessage(sess);

            msg.setSubject(
                    "Тревожное уведомление");
            msg.setRecipient(Message.RecipientType.TO,
                    new InternetAddress("nylaet@gmail.com"));

            BodyPart bodyPart = new MimeBodyPart();
            String text = "test";

            bodyPart.setText(
                    "Произошло срабатывание датчика! Обратите внимание! " + text);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(bodyPart);

            msg.setContent(multipart);

            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
