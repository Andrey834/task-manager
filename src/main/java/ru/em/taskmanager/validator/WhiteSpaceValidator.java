package ru.em.taskmanager.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.em.taskmanager.validator.annotation.WhiteSpace;

public class WhiteSpaceValidator implements ConstraintValidator<WhiteSpace, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && !s.contains(" ");
    }
}
