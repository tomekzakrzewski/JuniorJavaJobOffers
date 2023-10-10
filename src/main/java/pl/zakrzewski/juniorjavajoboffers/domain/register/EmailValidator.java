package pl.zakrzewski.juniorjavajoboffers.domain.register;

import java.util.regex.Pattern;

class EmailValidator {
    private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(emailRegex);
        if (pattern.matcher(email).matches()) {
            return true;
        }
        return false;
    }
}
