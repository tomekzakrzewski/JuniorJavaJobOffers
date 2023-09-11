package pl.zakrzewski.juniorjavajoboffers.domain.emailsender;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils.EmailSenderUtils;
import pl.zakrzewski.juniorjavajoboffers.domain.offer.dto.OfferDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailSenderService {
    private static final String USER_ACCCOUNT_VERIFICATION = "Confirm your account";
    private static final String JOB_OFFERS = "Job offers for junior java developer position";
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${spring.mail.verify.host}")
    private String host;

    @Async
    public void sendConfirmationEmail(String mail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(USER_ACCCOUNT_VERIFICATION);
            message.setFrom(fromEmail);
            message.setTo(mail);
            message.setText(EmailSenderUtils.getEmailConfirmationMessage(host, token));
            emailSender.send(message);
            log.info("Confirmation email send");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Confirmation email for token " + token + " not sent");
        }
    }

    @Async
    public void sendOffersEmail(List<String> emails, List<OfferDto> offers) {
        try {
            log.info("Started sending emails with job offers");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setText(offers.stream().toString());
            message.setSubject(JOB_OFFERS);
            for (String email : emails) {
                message.setTo(email);
                emailSender.send(message);
            }
            log.info("Sending emails with job offers completed");
        } catch (Exception e) {
            System.out.println(e);
            log.info("Error while sending emails with job offers");
        }
    }
}
