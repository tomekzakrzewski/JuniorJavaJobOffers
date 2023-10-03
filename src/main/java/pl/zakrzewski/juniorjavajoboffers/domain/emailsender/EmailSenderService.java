package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;
import pl.zakrzewski.juniorjavajoboffers.domain.register.dto.UserIdEmailDto;

import java.util.List;
import java.util.Map;

import static pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils.EmailSenderUtils.getUnsubscribeUrl;
import static pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils.EmailSenderUtils.getVerificationUrl;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailSenderService {
    private static final String USER_ACCOUNT_VERIFICATION = "Confirm your account";
    private static final String JOB_OFFERS = "Job offers for junior java developer position";
    private static final String UTF_8_ENCODING = "UTF-8";
    private static final String CONFIRMATION_EMAIL_TEMPLATE = "confirmationemailtemplate";
    private static final String OFFERS_EMAIL_TEMPLATE = "joboffersemailtemplate";
    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.verify.host}")
    private String host;

    @Async
    public void sendConfirmationEmail(String toEmail, String token) {
        try {
            MimeMessage confirmationEmail = createConfirmationEmail(toEmail, token);
            emailSender.send(confirmationEmail);
            log.info("Confirmation email sent");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Error while sending confirmation email");
        }
    }

    @Async
    public void sendOffersEmail(List<UserIdEmailDto> users, List<OfferDto> offers) {
        try {
            for (UserIdEmailDto user : users) {
                Context context = addUnsubscribe(user.id());
                MimeMessage message = createJobOffersEmail(offers, context);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.email()));
                emailSender.send(message);
            }
            log.info("Job offers email sent");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Sending job offers email failed");
        }
    }

    private MimeMessage createConfirmationEmail(String toEmail, String token) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("url", getVerificationUrl(host, token)));
            String text = templateEngine.process(CONFIRMATION_EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = getHelper(USER_ACCOUNT_VERIFICATION, message);
            helper.setTo(toEmail);
            helper.setText(text, true);
            return message;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private MimeMessage createJobOffersEmail(List<OfferDto> offers, Context context) {
        try {
            context.setVariables(Map.of("offerList", offers));
            String text = templateEngine.process(OFFERS_EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = getHelper(JOB_OFFERS, message);
            helper.setText(text, true);
            return message;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private Context addUnsubscribe(String id) {
        Context context = new Context();
        context.setVariable("url", getUnsubscribeUrl(host, id));
        return context;
    }


    private MimeMessageHelper getHelper(String subject, MimeMessage message) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
        helper.setPriority(1);
        helper.setSubject(subject);
        helper.setFrom(fromEmail);
        return helper;
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}







