package com.example.epolsoftbackend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {

    @Override
    public void initialize(PasswordConstraint contactNumber) {
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
        int min_lowercase_letter_number = 1;
        int min_uppercase_letter_number = 1;
        int min_digit_number = 1;
        int min_spec_symbol_number = 1;
        int min_char_number = 8;
        int max_char_number = 16;

        return contactField != null && contactField.matches("^(?=.*[a-z]{"
                + min_lowercase_letter_number + ",})(?=.*[A-Z]{"
                + min_uppercase_letter_number + ",})(?=.*[0-9]{"
                + min_digit_number + ",})(?=.*[@$!%?&]{"
                + min_spec_symbol_number + ",})[A-Za-z0-9@$!%?&]{"
                + min_char_number + ","
                + max_char_number + "}$");
    }

}
