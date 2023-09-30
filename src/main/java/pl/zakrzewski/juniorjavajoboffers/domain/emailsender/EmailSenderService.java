package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
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
            MimeMessage confirmationEmail = createConfimationEmail(toEmail, token);
            emailSender.send(confirmationEmail);
            log.info("Confirmation email sent");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Error while sending confirmation email");
        }
    }

    @Async
    public void sendOffersEmail(List<String> emails, List<OfferDto> offers) {
        try {
            MimeMessage jobOffersEmail = createJobOffersEmail(offers);
            for (String email : emails) {
                jobOffersEmail.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
                emailSender.send(jobOffersEmail);
        }
            log.info("Job offers email sent");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Sending job offers email failed");
        }
    }

    private MimeMessage createConfimationEmail(String toEmail, String token) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("url", getVerificationUrl(host, token)));
            String text = templateEngine.process(CONFIRMATION_EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setText(text, true);
            return message;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private MimeMessage createJobOffersEmail(List<OfferDto> offers) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("offerList", offers));
            String text = templateEngine.process(OFFERS_EMAIL_TEMPLATE, context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(JOB_OFFERS);
            helper.setFrom(fromEmail);
            helper.setText(text, true);
            return message;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private MimeMessage getMimeMessage() {
        return emailSender.createMimeMessage();
    }
}







