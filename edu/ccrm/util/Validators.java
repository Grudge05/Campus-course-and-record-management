package edu.ccrm.util;

public class Validators {
    public static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    public static boolean isValidId(String id) {
        return id != null && !id.trim().isEmpty();
    }

    public static boolean isValidCredits(int credits) {
        return credits > 0 && credits <= 6;
    }
}
