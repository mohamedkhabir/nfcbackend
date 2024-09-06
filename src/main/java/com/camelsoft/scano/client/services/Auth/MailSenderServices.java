package com.camelsoft.scano.client.services.Auth;

import com.camelsoft.scano.client.models.Auth.users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Service
public class MailSenderServices {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage preConfiguredMessage;


    public void sendEmailResetPassword(String content, users to , Date date) {
        SimpleMailMessage msg = new SimpleMailMessage(preConfiguredMessage);
        msg.setTo(to.getEmail());
        msg.setSubject("Scano Reset password");
        msg.setText("Hi "+to.getLastname()+"\n" +
                        "You recently requested to reset your password for your Scano App account. Use the code below to reset it. This password reset is only valid for " + date +". \n"+
                content
                +"\n" + "Thanks,\n Scano Team"
        );
        mailSender.send(msg);
    }

    public void sendContactUsEmail(String Name,String email,String phonenumber,String content,String reason) {

        SimpleMailMessage msg = new SimpleMailMessage(preConfiguredMessage);
        msg.setTo("test@camel-soft.com");

        msg.setSubject("Contact Us Form.");
        msg.setText(
                "This is a contact form."
                        +"\n"+ "\n"+
                        "Name: "+Name+"\n"+
                        "Email Address: "+email+"\n"+
                        "Phone Number: "+phonenumber+"\n"+
                        "Reason: "+reason+" \n "+
                        content);

        mailSender.send(msg);
    }



    public void sendEmail(String content, String subject) {

        SimpleMailMessage msg = new SimpleMailMessage(preConfiguredMessage);
        msg.setTo("contact@tuniboat.com");

        msg.setSubject(subject);
        msg.setText(content);

        mailSender.send(msg);
    }

    public void sendMail(String toEmail,String Subject,String Text){
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(toEmail);

        msg.setSubject(Subject);
        msg.setText(Text);

        mailSender.send(msg);


    }

    private void sendHtmlMessage(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        mailSender.send(message);
    }


    void sendEmailWithAttachment(String subject,String content, String to, File file) throws MessagingException, IOException {

        MimeMessage msg = mailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg,  "UTF-8");

        helper.setTo(to);

        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<h1>"+content+"</h1>", true);

        // hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));

        helper.addAttachment(file.getName(), file);

        this.mailSender.send(msg);

    }
    public void sendEmailHtml(String subject, String content, String to, String recivername) throws MessagingException, IOException {

        MimeMessage msg = mailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, false);

        helper.setTo(to);

        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
        helper.setText("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                "    <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                "    <!--[if mso]>\n" +
                "    <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "            <o:AllowPNG/>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "    </xml><![endif]-->\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Abril+Fatface\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Bitter\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Cabin\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <!--<![endif]-->\n" +
                "    <style>\n" +
                "        * {\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        a[x-apple-data-detectors] {\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: inherit !important;\n" +
                "        }\n" +
                "\n" +
                "        #MessageViewBody a {\n" +
                "            color: inherit;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            line-height: inherit\n" +
                "        }\n" +
                "\n" +
                "        .desktop_hide,\n" +
                "        .desktop_hide table {\n" +
                "            mso-hide: all;\n" +
                "            display: none;\n" +
                "            max-height: 0px;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        @media (max-width: 620px) {\n" +
                "            .desktop_hide table.icons-inner {\n" +
                "                display: inline-block !important;\n" +
                "            }\n" +
                "\n" +
                "            .icons-inner {\n" +
                "                text-align: center;\n" +
                "            }\n" +
                "\n" +
                "            .icons-inner td {\n" +
                "                margin: 0 auto;\n" +
                "            }\n" +
                "\n" +
                "            .row-content {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            .image_block img.big {\n" +
                "                width: auto !important;\n" +
                "            }\n" +
                "\n" +
                "            .column .border,\n" +
                "            .mobile_hide {\n" +
                "                display: none;\n" +
                "            }\n" +
                "\n" +
                "            table {\n" +
                "                table-layout: fixed !important;\n" +
                "            }\n" +
                "\n" +
                "            .stack .column {\n" +
                "                width: 100%;\n" +
                "                display: block;\n" +
                "            }\n" +
                "\n" +
                "            .mobile_hide {\n" +
                "                min-height: 0;\n" +
                "                max-height: 0;\n" +
                "                max-width: 0;\n" +
                "                overflow: hidden;\n" +
                "                font-size: 0px;\n" +
                "            }\n" +
                "\n" +
                "            .desktop_hide,\n" +
                "            .desktop_hide table {\n" +
                "                display: table !important;\n" +
                "                max-height: none !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body style=\"background-color: #FFFFFF; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\"\n" +
                "       style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF;\" width=\"100%\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                               role=\"presentation\"\n" +
                "                               style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;\"\n" +
                "                               width=\"600\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td class=\"column column-1\"\n" +
                "                                    style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "                                                <div align=\"center\" style=\"line-height:10px\"><img class=\"big\"\n" +
                "                                                                                                  src=\"https://server.coatchtime.client.com/mail/images/logo_book_and_boat-04.png\"\n" +
                "                                                                                                  style=\"display: block; height: auto; border: 0; width: 600px; max-width: 100%;\"\n" +
                "                                                                                                  width=\"600\"/></div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"divider_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div align=\"center\">\n" +
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                           role=\"presentation\"\n" +
                "                                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                                           width=\"100%\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td class=\"divider_inner\"\n" +
                "                                                                style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #BBBBBB;\">\n" +
                "                                                                <span> </span></td>\n" +
                "                                                        </tr>\n" +
                "                                                    </table>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-left:10px;padding-right:10px;padding-top:20px;text-align:center;width:100%;\">\n" +
                "                                                <h1 style=\"margin: 0; color: #191919; direction: ltr; font-family: 'Roboto', Tahoma, Verdana, Segoe, sans-serif; font-size: 24px; font-weight: 400; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0;\">\n" +
                "                                                    <strong>"+subject+" </strong></h1>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-left:10px;padding-right:10px;padding-top:15px;\">\n" +
                "                                                <div style=\"font-family: Arial, sans-serif\">\n" +
                "                                                    <div class=\"txtTinyMce-wrapper\"\n" +
                "                                                         style=\"font-size: 14px; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; mso-line-height-alt: 16.8px; color: #393d47; line-height: 1.2;\">\n" +
                "                                                        <p style=\"margin: 0; font-size: 14px; text-align: center;\"><span\n" +
                "                                                                style=\"font-size:18px;\">Votre compte a bien été créé avec succès</span>\n" +
                "                                                        </p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"color:#393d47;direction:ltr;font-family:'Roboto', Tahoma, Verdana, Segoe, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;\">\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\"> </p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Bonjour "+recivername+",</p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Bienvenue à bord de\n" +
                "                                                        Book&Boat ! Vous venez de rejoindre la communauté de passionnés\n" +
                "                                                        de la mer. </p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Profitez des annonces de\n" +
                "                                                        location de bateaux disponibles sur notre plateforme et\n" +
                "                                                        planifiez d’inoubliables escapades en mer entre amis ou en\n" +
                "                                                        famille !</p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Vous pouvez voir dès\n" +
                "                                                        maintenant les offres disponibles sur Book&Boat ou téléchargez\n" +
                "                                                        l’application Book&Boat depuis iPhone ou Android app store pour\n" +
                "                                                        une meilleure expérience !</p>\n" +
                "                                                    <p style=\"margin: 0;\"> </p>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"button_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div align=\"center\">\n" +
                "                                                    <!--[if mso]>\n" +
                "                                                    <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "                                                                 xmlns:w=\"urn:schemas-microsoft-com:office:word\"\n" +
                "                                                                 style=\"height:44px;width:304px;v-text-anchor:middle;\"\n" +
                "                                                                 arcsize=\"10%\" strokeweight=\"0.75pt\"\n" +
                "                                                                 strokecolor=\"#8a3b8f\" fillcolor=\"#295bac\">\n" +
                "                                                        <w:anchorlock/>\n" +
                "                                                        <v:textbox inset=\"0px,0px,0px,0px\">\n" +
                "                                                            <center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:16px\">\n" +
                "                                                    <![endif]-->\n" +
                "                                                    <div style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#295bac;border-radius:4px;width:auto;border-top:1px solid #8a3b8f;font-weight:400;border-right:1px solid #8a3b8f;border-bottom:1px solid #8a3b8f;border-left:1px solid #8a3b8f;padding-top:5px;padding-bottom:5px;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\">\n" +
                "                                                        <span style=\"padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;letter-spacing:normal;\"><span\n" +
                "                                                                style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\">Je découvre les bateaux Book&Boat</span></span>\n" +
                "                                                    </div>\n" +
                "                                                    <!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "                                                <div align=\"center\" style=\"line-height:10px\"><img\n" +
                "                                                        src=\"https://server.coatchtime.client.com/mail/images/down_app.PNG\"\n" +
                "                                                        style=\"display: block; height: auto; border: 0; width: 240px; max-width: 100%;\"\n" +
                "                                                        width=\"240\"/></div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"color:#393d47;direction:ltr;font-family:'Roboto', Tahoma, Verdana, Segoe, sans-serif;font-size:15px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;\">\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\"> </p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Chaleureusement,</p>\n" +
                "                                                    <p style=\"margin: 0;\">L’équipe Book&Boat</p>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-top:15px;text-align:center;width:100%;\">\n" +
                "                                                <h1 style=\"margin: 0; color: #f4b400; direction: ltr; font-family: 'Roboto', Tahoma, Verdana, Segoe, sans-serif; font-size: 24px; font-weight: 400; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0;\">\n" +
                "                                                    <span class=\"tinyMce-placeholder\">Que l’aventure commence !</span>\n" +
                "                                                </h1>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                               role=\"presentation\"\n" +
                "                               style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;\"\n" +
                "                               width=\"600\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td class=\"column column-1\"\n" +
                "                                    style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 20px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "                                                <div align=\"center\" style=\"line-height:10px\"><img\n" +
                "                                                        src=\"https://server.coatchtime.client.com/mail/images/Ajouter_un_titre.png\"\n" +
                "                                                        style=\"display: block; height: auto; border: 0; width: 330px; max-width: 100%;\"\n" +
                "                                                        width=\"330\"/></div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"font-family: sans-serif\">\n" +
                "                                                    <div class=\"txtTinyMce-wrapper\"\n" +
                "                                                         style=\"color: #C0C0C0; font-size: 12px; mso-line-height-alt: 14.399999999999999px; line-height: 1.2; font-family: Helvetica Neue, Helvetica, Arial, sans-serif;\">\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">Besoin d’aide ?\n" +
                "                                                            N'hésitez pas à nous contacter ! </p>\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">Téléphone/WhatsApp : \n" +
                "                                                            +216 29 62 13 00</p>\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">E-mail :\n" +
                "                                                            contact@bookandboat.com</p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"social_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                       class=\"social-table\" role=\"presentation\"\n" +
                "                                                       style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                                       width=\"168px\">\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.facebook.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Facebook\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/facebook2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"facebook\" width=\"32\"/></a></td>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.twitter.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Twitter\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/twitter2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"twitter\" width=\"32\"/></a></td>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.linkedin.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Linkedin\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/linkedin2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"linkedin\" width=\"32\"/></a></td>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.instagram.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Instagram\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/instagram2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"instagram\" width=\"32\"/></a></td>\n" +
                "                                                    </tr>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"font-family: sans-serif\">\n" +
                "                                                    <div class=\"txtTinyMce-wrapper\"\n" +
                "                                                         style=\"color: #C0C0C0; font-size: 12px; mso-line-height-alt: 14.399999999999999px; line-height: 1.2; font-family: Helvetica Neue, Helvetica, Arial, sans-serif;\">\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">Copyright © 2022\n" +
                "                                                            Book&Boat<br/>Vous avez changé d'avis ? Vous pouvez vous\n" +
                "                                                            désinscrire à tout moment.</p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                               role=\"presentation\"\n" +
                "                               style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;\"\n" +
                "                               width=\"600\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td class=\"column column-1\"\n" +
                "                                    style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "                                                       style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                                       width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"vertical-align: middle; text-align: center;\">\n" +
                "                                                            <!--[if vml]>\n" +
                "                                                            <table align=\"left\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                                   role=\"presentation\"\n" +
                "                                                                   style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\">\n" +
                "                                                            <![endif]-->\n" +
                "                                                            <!--[if !vml]><!-->\n" +
                "                                                            <table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\"\n" +
                "                                                                   role=\"presentation\"\n" +
                "                                                                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\">\n" +
                "                                                                <!--<![endif]-->\n" +
                "                                                            </table>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table><!-- End -->\n" +
                "</body>\n" +
                "</html>\n", true);


        // hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));


        this.mailSender.send(msg);

    }



    public void sendEmailHtml1(String subject, String content, String to, String recivername) throws MessagingException, IOException {

        MimeMessage msg = mailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, false);

        helper.setTo(to);

        helper.setSubject(subject);

        // default = text/plain
        //helper.setText("Check attachment for image!");
        //   this.mailSenderServices.sendEmailHtml("Welcome to Coach Web Site.","<!DOCTYPE html><html xmlns:v=");
        //urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office" lang="en"> <head>\n"
//					+
//"<title></title>\n"
//					+
//					"        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"> \n"
//					+
//					"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"+
//	"<!--[if mso]><xml><o:OfficeDocumentSettings><o:PixelsPerInch>96</o:PixelsPerInch><o:AllowPNG/></o:OfficeDocumentSettings></xml><![endif]-->\n" +
//	"<style>"+
//		"* {" +
//               "     box-sizing: border-box;"+
//               " }" +
//
//                "body {" +
//                    "margin: 0;"+
//                    "padding: 0;"+
//                "}"+
//                "a[x-apple-data-detectors] {"+
//                    "color: inherit !important;"+
//                    "text-decoration: inherit !important;"+
//                "}"+
//
//		"#MessageViewBody a {"+
//                    "color: inherit;"+
//                    "text-decoration: none;"+
//                "}"+
//
//                "p {"+
//                    "line-height: inherit"+
//               " }"+
//
//		".desktop_hide,"+
//		".desktop_hide table {"+
//                    "mso-hide: all;"+
//                   " display: none;"+
//                    "max-height: 0px;"+
//                    "overflow: hidden;"+
//                "}"+
//
//                "@media (max-width:695px) {"+
//			".desktop_hide table.icons-inner {"+
//                        "display: inline-block !important;"+
//                    "}"+
//
//			".icons-inner {"+
//                        "text-align: center;"+
//                    "}"+
//
//			".icons-inner td {"+
//                        "margin: 0 auto;"+
//                   " }"+
//
//			".image_block img.big,"+
//			".row-content {"+
//                        "width: 100% !important;"+
//                    "}"+
//
//			".mobile_hide {"+
//                        "display: none;"+
//                    "}"+
//			".stack .column {"+
//                       " width: 100%;"+
//                        "display: block;"+
//                    "}"+
//
//			".mobile_hide {"+
//                       " min-height: 0;"+
//                        "max-height: 0;"+
//                        "max-width: 0;"+
//                        "overflow: hidden;"+
//                        "font-size: 0px;"+
//                    "}"+
//
//			".desktop_hide,"+
//			".desktop_hide table {"+
//                        "display: table !important;"+
//                        "max-height: none !important;"+
//                    "}"+
//                "}"+
//	"</style>"+
//"</head>"+
//
//"<body style=\"background-color: #f9f9f9; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">"+
//	"<table class=\"nl-container\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #f9f9f9;\">"+
//		"<tbody>"+
//			"<tr>"+
//				"<td>"+
//					"<table class="row row-1" align="center" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">"
//						"<tbody>
//							"<tr>"
//								<td>
//									<table class="row-content stack" align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 675px;" width="675">
//										"<tbody>
//											"<tr>"
//												<td class="column column-1" width="100%" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;">
//													<table class="image_block block-2" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//														"<tr>"
//															<td class="pad" style="width:100%;padding-right:0px;padding-left:0px;padding-top:10px;">
//																<div class="alignment" align="center" style="line-height:10px"><img src="https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/919758_904118/Group%205168%402x.png" style="display: block; height: auto; border: 0; width: 134px; max-width: 100%;" width="134"></div>
//															</td>
//														"</tr>"
//													</table>
//												</td>
//											"</tr>"
//										"</tbody>"
//									</table>
//								</td>
//							"</tr>"
//						"</tbody>"
//					</table>
//					<table class="row row-2" align="center" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//						"<tbody>
//							"<tr>"
//								<td>
//									<table class="row-content stack" align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 675px;" width="675">
//										"<tbody>
//											"<tr>"
//												<td class="column column-1" width="100%" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;">
//													<div class="spacer_block" style="height:15px;line-height:15px;font-size:1px;">&#8202;</div>
//												</td>
//											"</tr>"
//										"</tbody>"
//									</table>
//								</td>
//							"</tr>"
//						"</tbody>"
//					</table>
//					<table class="row row-3" align="center" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//						"<tbody>
//							"<tr>"
//								<td>
//									<table class="row-content stack" align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; border-radius: 0; width: 675px;" width="675">
//										"<tbody>
//											"<tr>"
//												<td class="column column-1" width="100%" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;">
//													<table class="image_block block-1" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//														"<tr>"
//															<td class="pad" style="width:100%;padding-right:0px;padding-left:0px;">
//																<div class="alignment" align="center" style="line-height:10px"><img class="big" src="https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/919758_904118/Group%205361.png" style="display: block; height: auto; border: 0; width: 675px; max-width: 100%;" width="675" alt="I'm an image" title="I'm an image"></div>
//															</td>
//														"</tr>"
//													</table>
//													<table class="text_block block-2" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;">
//														"<tr>"
//															<td class="pad" style="padding-bottom:10px;padding-left:40px;padding-right:40px;padding-top:10px;">
//																<div style="font-family: sans-serif">
//																	<div class style="font-size: 12px; mso-line-height-alt: 18px; color: #191919; line-height: 1.5; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;">
//																		<p style="margin: 0; font-size: 16px; text-align: center; mso-line-height-alt: 24px;"><strong><span style="font-size:38px;">Hi , </span></strong></p>
//																		<p style="margin: 0; font-size: 16px; text-align: center; mso-line-height-alt: 24px;"><strong><span style="font-size:38px;">welcome to Coaching website!</span></strong></p>
//																	</div>
//																</div>
//															</td>
//														"</tr>"
//													</table>
//													<table class="text_block block-3" width="100%" border="0" cellpadding="10" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;">
//														"<tr>"
//															<td class="pad">
//																<div style="font-family: sans-serif">
//																	<div class style="font-size: 12px; mso-line-height-alt: 14.399999999999999px; color: #191919; line-height: 1.2; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;">
//																		<p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 16.8px;"><span style="font-size:22px;">Thank you for subscribing!</span></p>
//																	</div>
//																</div>
//															</td>
//														"</tr>"
//													</table>
//												</td>
//											"</tr>"
//										"</tbody>"
//									</table>
//								</td>
//							"</tr>"
//						"</tbody>"
//					</table>
//					<table class="row row-4" align="center" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//						"<tbody>
//							"<tr>"
//								<td>
//									<table class="row-content stack" align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 675px;" width="675">
//										"<tbody>
//											"<tr>"
//												<td class="column column-1" width="100%" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;">
//													<table class="text_block block-2" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;">
//														"<tr>"
//															<td class="pad" style="padding-bottom:10px;padding-left:40px;padding-right:40px;padding-top:25px;">
//																<div style="font-family: sans-serif">
//																	<div class style="font-size: 12px; mso-line-height-alt: 24px; color: #555555; line-height: 2; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;">
//																		<p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 28px;">If you have any questions, feel free message us at support@mailus.com. All right reserved. Update <a style="text-decoration: none; color: #555555;" href="#" target="_blank" rel="noopener">email preferences</a> or <a style="text-decoration: none; color: #555555;" href="#" target="_blank" rel="noopener">unsubscribe</a>.</p>
//																	</div>
//																</div>
//															</td>
//														"</tr>"
//													</table>
//													<table class="text_block block-3" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;">
//														"<tr>"
//															<td class="pad" style="padding-bottom:10px;padding-left:40px;padding-right:40px;">
//																<div style="font-family: sans-serif">
//																	<div class style="font-size: 12px; mso-line-height-alt: 24px; color: #555555; line-height: 2; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif;">
//																		<p style="margin: 0; font-size: 14px; text-align: center; mso-line-height-alt: 28px;">5781 Spring St Salinas, Idaho 88606 United States</p>
//																	</div>
//																</div>
//															</td>
//														"</tr>"
//													</table>
//												</td>
//											"</tr>"
//										"</tbody>"
//									</table>
//								</td>
//							"</tr>"
//						"</tbody>"
//					</table>
//					<table class="row row-5" align="center" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//						"<tbody>
//							"<tr>"
//								<td>
//									<table class="row-content stack" align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff; color: #000000; width: 675px;" width="675">
//										"<tbody>
//											"<tr>"
//												<td class="column column-1" width="100%" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;">
//													<table class="text_block block-1" width="100%" border="0" cellpadding="10" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;">
//														"<tr>"
//															<td class="pad">
//																<div style="font-family: sans-serif">
//																	<div class style="font-size: 12px; font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; mso-line-height-alt: 14.399999999999999px; color: #ffffff; line-height: 1.2;">
//																		<p style="margin: 0; font-size: 12px; text-align: center; mso-line-height-alt: 14.399999999999999px;"><span style="color:#555555;">Terms of use <strong>|</strong> Privacy Policy</span></p>
//																	</div>
//																</div>
//															</td>
//														"</tr>"
//													</table>
//												</td>
//											"</tr>"
//										"</tbody>"
//									</table>
//								</td>
//							"</tr>"
//						"</tbody>"
//					</table>
//					<table class="row row-6" align="center" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//						"<tbody>
//							"<tr>"
//								<td>
//									<table class="row-content stack" align="center" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 675px;" width="675">
//										"<tbody>
//											"<tr>"
//												<td class="column column-1" width="100%" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;">
//													<table class="icons_block block-1" width="100%" border="0" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//														"<tr>"
//															<td class="pad" style="vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;">
//																<table width="100%" cellpadding="0" cellspacing="0" role="presentation" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt;">
//																	"<tr>"
//																		<td class="alignment" style="vertical-align: middle; text-align: center;">
//																			<!--[if vml]><table align="left" cellpadding="0" cellspacing="0" role="presentation" style="display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;"><![endif]-->
//																			<!--[if !vml]><!-->
//																			<table class="icons-inner" style="mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;" cellpadding="0" cellspacing="0" role="presentation">
//																				<!--<![endif]-->
//																				"<tr>"
//																					<td style="vertical-align: middle; text-align: center; padding-top: 5px; padding-bottom: 5px; padding-left: 5px; padding-right: 6px;"><a href="https://www.designedwithbee.com/" target="_blank" style="text-decoration: none;"><img class="icon" alt="Designed with BEE" src="https://d15k2d11r6t6rl.cloudfront.net/public/users/Integrators/BeeProAgency/53601_510656/Signature/bee.png" height="32" width="34" align="center" style="display: block; height: auto; margin: 0 auto; border: 0;"></a></td>
//																					<td style="font-family: Montserrat, Trebuchet MS, Lucida Grande, Lucida Sans Unicode, Lucida Sans, Tahoma, sans-serif; font-size: 15px; color: #9d9d9d; vertical-align: middle; letter-spacing: undefined; text-align: center;"><a href="https://www.designedwithbee.com/" target="_blank" style="color: #9d9d9d; text-decoration: none;">Designed with BEE</a></td>
//																				"</tr>"
//																			</table>
//																		</td>
//																	"</tr>"
//																</table>
//															</td>
//														"</tr>"
//													</table>
//												</td>
//											"</tr>"
//										"</tbody>"
//									</table>
//								</td>
//							"</tr>"
//						"</tbody>"
//					</table>
//				</td>
//			"</tr>"
//		"</tbody>"
//	</table><!-- End -->
//</body>
//
//</html>);
        // true = text/html
        helper.setText("<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\" xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:v=\"urn:schemas-microsoft-com:vml\">\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\"/>\n" +
                "    <meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\"/>\n" +
                "    <!--[if mso]>\n" +
                "    <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "            <o:AllowPNG/>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "    </xml><![endif]-->\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Abril+Fatface\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Bitter\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Montserrat\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Lato\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Cabin\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <link href=\"https://fonts.googleapis.com/css?family=Roboto\" rel=\"stylesheet\" type=\"text/css\"/>\n" +
                "    <!--<![endif]-->\n" +
                "    <style>\n" +
                "        * {\n" +
                "            box-sizing: border-box;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        a[x-apple-data-detectors] {\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: inherit !important;\n" +
                "        }\n" +
                "\n" +
                "        #MessageViewBody a {\n" +
                "            color: inherit;\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            line-height: inherit\n" +
                "        }\n" +
                "\n" +
                "        .desktop_hide,\n" +
                "        .desktop_hide table {\n" +
                "            mso-hide: all;\n" +
                "            display: none;\n" +
                "            max-height: 0px;\n" +
                "            overflow: hidden;\n" +
                "        }\n" +
                "\n" +
                "        @media (max-width: 620px) {\n" +
                "            .desktop_hide table.icons-inner {\n" +
                "                display: inline-block !important;\n" +
                "            }\n" +
                "\n" +
                "            .icons-inner {\n" +
                "                text-align: center;\n" +
                "            }\n" +
                "\n" +
                "            .icons-inner td {\n" +
                "                margin: 0 auto;\n" +
                "            }\n" +
                "\n" +
                "            .row-content {\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            .image_block img.big {\n" +
                "                width: auto !important;\n" +
                "            }\n" +
                "\n" +
                "            .column .border,\n" +
                "            .mobile_hide {\n" +
                "                display: none;\n" +
                "            }\n" +
                "\n" +
                "            table {\n" +
                "                table-layout: fixed !important;\n" +
                "            }\n" +
                "\n" +
                "            .stack .column {\n" +
                "                width: 100%;\n" +
                "                display: block;\n" +
                "            }\n" +
                "\n" +
                "            .mobile_hide {\n" +
                "                min-height: 0;\n" +
                "                max-height: 0;\n" +
                "                max-width: 0;\n" +
                "                overflow: hidden;\n" +
                "                font-size: 0px;\n" +
                "            }\n" +
                "\n" +
                "            .desktop_hide,\n" +
                "            .desktop_hide table {\n" +
                "                display: table !important;\n" +
                "                max-height: none !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body style=\"background-color: #FFFFFF; margin: 0; padding: 0; -webkit-text-size-adjust: none; text-size-adjust: none;\">\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\"\n" +
                "       style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF;\" width=\"100%\">\n" +
                "    <tbody>\n" +
                "    <tr>\n" +
                "        <td>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-1\" role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                               role=\"presentation\"\n" +
                "                               style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;\"\n" +
                "                               width=\"600\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td class=\"column column-1\"\n" +
                "                                    style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 0px; padding-bottom: 0px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "                                                <div align=\"center\" style=\"line-height:10px\"><img class=\"big\"\n" +
                "                                                                                                  src=\"https://server.coatchtime.client.com/mail/images/logo_book_and_boat-04.png\"\n" +
                "                                                                                                  style=\"display: block; height: auto; border: 0; width: 600px; max-width: 100%;\"\n" +
                "                                                                                                  width=\"600\"/></div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"divider_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div align=\"center\">\n" +
                "                                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                           role=\"presentation\"\n" +
                "                                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                                           width=\"100%\">\n" +
                "                                                        <tr>\n" +
                "                                                            <td class=\"divider_inner\"\n" +
                "                                                                style=\"font-size: 1px; line-height: 1px; border-top: 1px solid #BBBBBB;\">\n" +
                "                                                                <span> </span></td>\n" +
                "                                                        </tr>\n" +
                "                                                    </table>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-left:10px;padding-right:10px;padding-top:20px;text-align:center;width:100%;\">\n" +
                "                                                <h1 style=\"margin: 0; color: #191919; direction: ltr; font-family: 'Roboto', Tahoma, Verdana, Segoe, sans-serif; font-size: 24px; font-weight: 400; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0;\">\n" +
                "                                                    <strong>"+subject+" </strong></h1>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"text_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-left:10px;padding-right:10px;padding-top:15px;\">\n" +
                "                                                <div style=\"font-family: Arial, sans-serif\">\n" +
                "                                                    <div class=\"txtTinyMce-wrapper\"\n" +
                "                                                         style=\"font-size: 14px; font-family: Arial, 'Helvetica Neue', Helvetica, sans-serif; mso-line-height-alt: 16.8px; color: #393d47; line-height: 1.2;\">\n" +
                "                                                        <p style=\"margin: 0; font-size: 14px; text-align: center;\"><span\n" +
                "                                                                style=\"font-size:18px;\">Votre compte a bien été créé avec succès</span>\n" +
                "                                                        </p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"color:#393d47;direction:ltr;font-family:'Roboto', Tahoma, Verdana, Segoe, sans-serif;font-size:16px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;\">\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\"> </p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Bonjour "+recivername+",</p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Bienvenue à bord de\n" +
                "                                                        Book&Boat ! Vous venez de rejoindre la communauté de passionnés\n" +
                "                                                        de la mer. </p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Profitez des annonces de\n" +
                "                                                        location de bateaux disponibles sur notre plateforme et\n" +
                "                                                        planifiez d’inoubliables escapades en mer entre amis ou en\n" +
                "                                                        famille !</p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Vous pouvez voir dès\n" +
                "                                                        maintenant les offres disponibles sur Book&Boat ou téléchargez\n" +
                "                                                        l’application Book&Boat depuis iPhone ou Android app store pour\n" +
                "                                                        une meilleure expérience !</p>\n" +
                "                                                    <p style=\"margin: 0;\"> </p>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"button_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div align=\"center\">\n" +
                "                                                    <!--[if mso]>\n" +
                "                                                    <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\"\n" +
                "                                                                 xmlns:w=\"urn:schemas-microsoft-com:office:word\"\n" +
                "                                                                 style=\"height:44px;width:304px;v-text-anchor:middle;\"\n" +
                "                                                                 arcsize=\"10%\" strokeweight=\"0.75pt\"\n" +
                "                                                                 strokecolor=\"#8a3b8f\" fillcolor=\"#295bac\">\n" +
                "                                                        <w:anchorlock/>\n" +
                "                                                        <v:textbox inset=\"0px,0px,0px,0px\">\n" +
                "                                                            <center style=\"color:#ffffff; font-family:Arial, sans-serif; font-size:16px\">\n" +
                "                                                    <![endif]-->\n" +
                "                                                    <div style=\"text-decoration:none;display:inline-block;color:#ffffff;background-color:#295bac;border-radius:4px;width:auto;border-top:1px solid #8a3b8f;font-weight:400;border-right:1px solid #8a3b8f;border-bottom:1px solid #8a3b8f;border-left:1px solid #8a3b8f;padding-top:5px;padding-bottom:5px;font-family:Helvetica Neue, Helvetica, Arial, sans-serif;text-align:center;mso-border-alt:none;word-break:keep-all;\">\n" +
                "                                                        <span style=\"padding-left:20px;padding-right:20px;font-size:16px;display:inline-block;letter-spacing:normal;\"><span\n" +
                "                                                                style=\"font-size: 16px; line-height: 2; word-break: break-word; mso-line-height-alt: 32px;\">Je découvre les bateaux Book&Boat</span></span>\n" +
                "                                                    </div>\n" +
                "                                                    <!--[if mso]></center></v:textbox></v:roundrect><![endif]-->\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "                                                <div align=\"center\" style=\"line-height:10px\"><img\n" +
                "                                                        src=\"https://server.coatchtime.client.com/mail/images/down_app.PNG\"\n" +
                "                                                        style=\"display: block; height: auto; border: 0; width: 240px; max-width: 100%;\"\n" +
                "                                                        width=\"240\"/></div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"paragraph_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"color:#393d47;direction:ltr;font-family:'Roboto', Tahoma, Verdana, Segoe, sans-serif;font-size:15px;font-weight:400;letter-spacing:0px;line-height:120%;text-align:left;\">\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\"> </p>\n" +
                "                                                    <p style=\"margin: 0; margin-bottom: 16px;\">Chaleureusement,</p>\n" +
                "                                                    <p style=\"margin: 0;\">L’équipe Book&Boat</p>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"heading_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"padding-top:15px;text-align:center;width:100%;\">\n" +
                "                                                <h1 style=\"margin: 0; color: #f4b400; direction: ltr; font-family: 'Roboto', Tahoma, Verdana, Segoe, sans-serif; font-size: 24px; font-weight: 400; letter-spacing: normal; line-height: 120%; text-align: center; margin-top: 0; margin-bottom: 0;\">\n" +
                "                                                    <span class=\"tinyMce-placeholder\">Que l’aventure commence !</span>\n" +
                "                                                </h1>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-2\" role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #ffffff;\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                               role=\"presentation\"\n" +
                "                               style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;\"\n" +
                "                               width=\"600\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td class=\"column column-1\"\n" +
                "                                    style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 20px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"image_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"width:100%;padding-right:0px;padding-left:0px;\">\n" +
                "                                                <div align=\"center\" style=\"line-height:10px\"><img\n" +
                "                                                        src=\"https://server.coatchtime.client.com/mail/images/Ajouter_un_titre.png\"\n" +
                "                                                        style=\"display: block; height: auto; border: 0; width: 330px; max-width: 100%;\"\n" +
                "                                                        width=\"330\"/></div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"font-family: sans-serif\">\n" +
                "                                                    <div class=\"txtTinyMce-wrapper\"\n" +
                "                                                         style=\"color: #C0C0C0; font-size: 12px; mso-line-height-alt: 14.399999999999999px; line-height: 1.2; font-family: Helvetica Neue, Helvetica, Arial, sans-serif;\">\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">Besoin d’aide ?\n" +
                "                                                            N'hésitez pas à nous contacter ! </p>\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">Téléphone/WhatsApp : \n" +
                "                                                            +216 29 62 13 00</p>\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">E-mail :\n" +
                "                                                            contact@bookandboat.com</p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"social_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                       class=\"social-table\" role=\"presentation\"\n" +
                "                                                       style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                                       width=\"168px\">\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.facebook.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Facebook\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/facebook2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"facebook\" width=\"32\"/></a></td>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.twitter.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Twitter\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/twitter2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"twitter\" width=\"32\"/></a></td>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.linkedin.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Linkedin\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/linkedin2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"linkedin\" width=\"32\"/></a></td>\n" +
                "                                                        <td style=\"padding:0 5px 0 5px;\"><a\n" +
                "                                                                href=\"https://www.instagram.com/\" target=\"_blank\"><img\n" +
                "                                                                alt=\"Instagram\" height=\"32\" src=\"https://server.coatchtime.client.com/mail/images/instagram2x.png\"\n" +
                "                                                                style=\"display: block; height: auto; border: 0;\"\n" +
                "                                                                title=\"instagram\" width=\"32\"/></a></td>\n" +
                "                                                    </tr>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                    <table border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"text_block\"\n" +
                "                                           role=\"presentation\"\n" +
                "                                           style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; word-break: break-word;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td>\n" +
                "                                                <div style=\"font-family: sans-serif\">\n" +
                "                                                    <div class=\"txtTinyMce-wrapper\"\n" +
                "                                                         style=\"color: #C0C0C0; font-size: 12px; mso-line-height-alt: 14.399999999999999px; line-height: 1.2; font-family: Helvetica Neue, Helvetica, Arial, sans-serif;\">\n" +
                "                                                        <p style=\"margin: 0; text-align: center;\">Copyright © 2022\n" +
                "                                                            Book&Boat<br/>Vous avez changé d'avis ? Vous pouvez vous\n" +
                "                                                            désinscrire à tout moment.</p>\n" +
                "                                                    </div>\n" +
                "                                                </div>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row row-3\" role=\"presentation\"\n" +
                "                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\" width=\"100%\">\n" +
                "                <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"row-content stack\"\n" +
                "                               role=\"presentation\"\n" +
                "                               style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; color: #000000; width: 600px;\"\n" +
                "                               width=\"600\">\n" +
                "                            <tbody>\n" +
                "                            <tr>\n" +
                "                                <td class=\"column column-1\"\n" +
                "                                    style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; font-weight: 400; text-align: left; vertical-align: top; padding-top: 5px; padding-bottom: 5px; border-top: 0px; border-right: 0px; border-bottom: 0px; border-left: 0px;\"\n" +
                "                                    width=\"100%\">\n" +
                "                                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"icons_block\"\n" +
                "                                           role=\"presentation\" style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                           width=\"100%\">\n" +
                "                                        <tr>\n" +
                "                                            <td style=\"vertical-align: middle; color: #9d9d9d; font-family: inherit; font-size: 15px; padding-bottom: 5px; padding-top: 5px; text-align: center;\">\n" +
                "                                                <table cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"\n" +
                "                                                       style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt;\"\n" +
                "                                                       width=\"100%\">\n" +
                "                                                    <tr>\n" +
                "                                                        <td style=\"vertical-align: middle; text-align: center;\">\n" +
                "                                                            <!--[if vml]>\n" +
                "                                                            <table align=\"left\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                "                                                                   role=\"presentation\"\n" +
                "                                                                   style=\"display:inline-block;padding-left:0px;padding-right:0px;mso-table-lspace: 0pt;mso-table-rspace: 0pt;\">\n" +
                "                                                            <![endif]-->\n" +
                "                                                            <!--[if !vml]><!-->\n" +
                "                                                            <table cellpadding=\"0\" cellspacing=\"0\" class=\"icons-inner\"\n" +
                "                                                                   role=\"presentation\"\n" +
                "                                                                   style=\"mso-table-lspace: 0pt; mso-table-rspace: 0pt; display: inline-block; margin-right: -4px; padding-left: 0px; padding-right: 0px;\">\n" +
                "                                                                <!--<![endif]-->\n" +
                "                                                            </table>\n" +
                "                                                        </td>\n" +
                "                                                    </tr>\n" +
                "                                                </table>\n" +
                "                                            </td>\n" +
                "                                        </tr>\n" +
                "                                    </table>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "                </tbody>\n" +
                "            </table>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    </tbody>\n" +
                "</table><!-- End -->\n" +
                "</body>\n" +
                "</html>\n", true);


        // hard coded a file path
        //FileSystemResource file = new FileSystemResource(new File("path/android.png"));


        this.mailSender.send(msg);

    }
}
