package com.red.persistence.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, com.red.persistence.model.Email>
{
    private Pattern pattern;
    private Matcher matcher;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    public void initialize(Email constraintAnnotation)
    {}

    @Override
    public boolean isValid(com.red.persistence.model.Email email, ConstraintValidatorContext context)
    {
        return (validateEmail(email));
    }

    private boolean validateEmail(com.red.persistence.model.Email email)
    {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email.getAddress());
        return matcher.matches();
    }
}
