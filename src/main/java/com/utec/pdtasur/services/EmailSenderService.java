package com.utec.pdtasur.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailSenderService {
    public void sendEmailRegistro(String recipient, String nombre, int numero) {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/app.properties")) {
            props.load(input);

        }catch (IOException e){
            e.printStackTrace();
        }

        String username = props.getProperty("username");
        String password = props.getProperty("password");


        // Crear sesión con autenticación
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Notificación de Cuenta Creada");

            // Contenido HTML
            String htmlContent = """
                    <!DOCTYPE html>
                    <html lang="es">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Email Template</title>
                        <style>
                            body, table, td, a {
                                -webkit-text-size-adjust: 100%;
                                -ms-text-size-adjust: 100%;
                                font-family: sans-serif;
                            }
                            table, td {
                                mso-table-lspace: 0pt;
                                mso-table-rspace: 0pt;
                            }
                            img {
                                -ms-interpolation-mode: bicubic;
                                border: 0;
                                height: auto;
                                line-height: 100%;
                                outline: none;
                                text-decoration: none;
                            }
                            table {
                                border-collapse: collapse !important;
                            }
                            body {
                                width: 100% !important;
                                height: 100% !important;
                                margin: 0 !important;
                                padding: 0 !important;
                            }
                                        
                            .container {
                                width: 100%;
                                max-width: 600px;
                            }
                            .content {
                                padding: 20px;
                                background-color: #ffffff;
                                border-radius: 4px;
                            }
                            .header {
                                padding: 40px 20px;
                                background-color: #fef200;
                                color: #3b3e99;
                                text-align: center;
                                font-size: 48px;
                                font-weight: bold;
                            }
                            .body-text {
                                color: #333333;
                                font-size: 16px;
                                line-height: 24px;
                            }
                            .footer {
                                padding: 10px;
                                font-size: 12px;
                                color: #666666;
                                text-align: center;
                            }
                                        
                            @media screen and (max-width: 600px) {
                                .header, .content {
                                    padding: 10px;
                                }
                                .header {
                                    font-size: 24px;
                                }
                                .body-text, .footer {
                                    font-size: 14px;
                                }
                            }
                        </style>
                    </head>
                    <body style="background-color: #f4f4f4; margin: 0; padding: 0;">
                        <table border="0" cellpadding="0" cellspacing="0" width="100%">
                            <tr>
                                <td align="center" style="padding: 20px 10px;">
                                    <table class="container" border="0" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td class="header">
                                                ASUR
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="content">
                                                <h1 style="font-size: 24px; font-weight: bold; color: #333333; margin: 0;">¡Hola""" +  " " + nombre +
                    """
                    !</h1>
                    <p class="body-text" style="margin: 20px 0;">Gracias por registrarse en nuestro sistema. Nos complace informarle que su solicitud ha sido recibida y está actualmente en proceso de aprobación.</p>
                    <p class="body-text">Su Número de Usuario es: <strong style="color: #3b3e99;">""" + numero +
                    """
                    </strong></p>
                    <p class="body-text" style="margin: 20px 0;">Le recomendamos conservar este número, ya que le será útil para futuras interacciones dentro del sistema.</p>
                    <p class="body-text" style="margin: 20px 0;">Le enviaremos una notificación adicional una vez que su registro haya sido aprobado. Si tiene alguna pregunta, no dude en ponerse en contacto con nuestro equipo de soporte.</p>
                    <p class="body-text" style="margin: 20px 0;">Atentamente, el equipo de ASUR</p>
                   \s
                </td>
            </tr>
        </table>
    </td>
</tr>
</table>
</body>
</html>
""";

            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport.send(message);
            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
