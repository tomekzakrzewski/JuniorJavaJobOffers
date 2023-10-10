package pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils;

public class EmailSenderUtils {

    public static String getVerificationUrl(String host, String token) {
        return host + "/api/v1/registration?token=" + token;
    }
    public static String getUnsubscribeUrl(String host, String id) {
        return host + "/api/v1/registration/unsubscribe?id=" + id;
    }
}
