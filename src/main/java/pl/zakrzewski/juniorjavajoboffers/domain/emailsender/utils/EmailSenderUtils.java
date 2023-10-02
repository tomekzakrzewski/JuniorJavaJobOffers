package pl.zakrzewski.juniorjavajoboffers.domain.emailsender.utils;

public class EmailSenderUtils {
    public static String getEmailConfirmationMessage(String host, String token) {
        return "Your account has been created. Please click the link below to verify your account. \n\n" + getVerificationUrl(host, token);
    }

    public static String getVerificationUrl(String host, String token) {
        return host + "/api/v1/registration?token=" + token;
    }

    public static String getUnsubscribeUrl(String host, String id) {
        return host + "/api/v1/registration/unsubscribe?id=" + id;
    }

}
