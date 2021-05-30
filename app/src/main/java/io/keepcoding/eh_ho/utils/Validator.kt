package io.keepcoding.eh_ho.utils

import android.util.Patterns
import java.util.regex.Pattern

object Validator {

    /**
     * Checks if the name is valid.
     * @param username - can be EditText or String
     * @return - true if the name is valid.
     */
    fun isValidUsername(username: String): Boolean {
        return username.trim().length > 5
    }

    /**
     * Checks if the email is valid.
     * @param email - can be EditText or String
     * @return - true if the email is valid.
     */
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Checks if the password is valid as per the following password policy.
     * Password should be minimum minimum 8 characters long.
     * Password should contain at least one number.
     * Password should contain at least one capital letter.
     * Password should contain at least one small letter.
     * Password should contain at least one special character.
     * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
     *
     * @param password - can be EditText or String
     * @return - true if the password is valid as per the password policy.
     */
    fun isValidPassword(password: String): Boolean {
        var valid = true

        // Password policy check
        // Password should be minimum minimum 8 characters long
        if (password.length < 8) {
            valid = false
        }
        // Password should contain at least one number
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one capital letter
        exp = ".*[A-Z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one small letter
        exp = ".*[a-z].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        // Password should contain at least one special character
        // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
        exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
        pattern = Pattern.compile(exp)
        matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            valid = false
        }

        return valid
    }
}
