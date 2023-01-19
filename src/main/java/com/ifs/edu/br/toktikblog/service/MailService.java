package com.ifs.edu.br.toktikblog.service;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.User;
import com.ifs.edu.br.toktikblog.structure.graph.Graph;
import com.ifs.edu.br.toktikblog.structure.graph.GraphException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@Service
public class MailService {

    private Graph<User> graph = PersistenceContext.userGraph;

    @Value("${service.mail.user}")
    private String user;

    @Value("${service.mail.gmail_password_app}")
    private String password;

    public void sendMail(User user){

        try {
            Message message = loadConfigsSMPT();

            // Remetente  - (default: MailCredentials.USER)
            message.setFrom(new InternetAddress(user.getEmail()));

            // Lista de Destinatário(s)
            var amigos = graph.getListOfOutputVertices(user);

            var emailList = getUsersEmail(amigos);
            Address[] toUsers = InternetAddress.parse(emailList);

            message.setRecipients(Message.RecipientType.TO, toUsers);
            message.setSubject("TOKTIK");
            message.setText(user.getNome() + " acabou de postar uma nova publicação, venha já conferir.");

            Transport.send(message);

        } catch (MessagingException | GraphException e) {
            e.printStackTrace();
        }
    }

    // extrai email dos usuários e monta uma string contendo todos
    // os emails.
    private String getUsersEmail(List<User> users) {
        StringBuilder emailList = new StringBuilder();
        for (User user : users) {
            emailList.append(user.getEmail()).append(",");
        }
        return emailList.toString();
    }

    // smtp configs
    private MimeMessage loadConfigsSMPT(){

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(
                props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, password);
                    }
                }
        );
        //session.setDebug(true); /* Ativa Debug da seção no terminal */

        return new MimeMessage(session);
    }
}
