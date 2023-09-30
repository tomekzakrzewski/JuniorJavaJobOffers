package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils.EmailSenderUtils;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;

import java.util.List;
import java.util.Map;

import static pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils.EmailSenderUtils.getVerificationUrl;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailSenderService {
    private static final String USER_ACCOUNT_VERIFICATION = "Confirm your account";
    private static final String JOB_OFFERS = "Job offers for junior java developer position";
    public static final String EMAIL_TEMPLATE = "emailtemplate";
    public static final String TEXT_HTML_ENCONDING = "text/html";
    private static final String UTF_8_ENCODING = "UTF-8";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.verify.host}")
    private String host;
//
//    @Async
//    public void sendConfirmationEmail(String mail, String token) {
//        try {
//            SimpleMailMessage message = createConfirmationEmail(mail, token);
//            emailSender.send(message);
//            log.info("Confirmation email sent");
//        } catch (Exception e) {
//            System.out.println(e);
//            log.info("Confirmation email for token " + token + " not sent");
//        }
//    }

    @Async
    public void sendConfirmationEmail(String toEmail, String token) {
        sendConfirmationMailx(toEmail, token);

    }

    @Async
    public void sendOffersEmail(List<String> emails, List<OfferDto> offers) {
        try {
            SimpleMailMessage message = createOffersEmail(offers);
            for (String email : emails) {
                message.setTo(email);
                emailSender.send(message);
            }
            log.info("Job offers emails sent");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Sending job offers email failed");
        }
    }

    private SimpleMailMessage createOffersEmail(List<OfferDto> offers) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setText(offers.toString());
        message.setSubject(JOB_OFFERS);
        return message;
    }

    private SimpleMailMessage createConfirmationEmail(String mail, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(USER_ACCOUNT_VERIFICATION);
        message.setFrom(fromEmail);
        message.setTo(mail);
        message.setText(EmailSenderUtils.getEmailConfirmationMessage(host, token));
        return message;
    }

    @Async
    public void sendConfirmationMailx(String toEmail, String token) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", "Tomek", "url", getVerificationUrl(host, token)));
            String text = templateEngine.process("emailtemplate", context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setText(text, true);

//            // Add images to the email body
//            BodyPart imageBodyPart = new MimeBodyPart();
//            DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/Downloads/images/dog.jpg");
//            imageBodyPart.setDataHandler(new DataHandler(dataSource));
//            imageBodyPart.setHeader("Content-ID", "image");
//            mimeMultipart.addBodyPart(imageBodyPart);

            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }


}







