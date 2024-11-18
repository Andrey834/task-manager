package ru.em.taskmanager.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.em.taskmanager.validator.WhiteSpaceValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = WhiteSpaceValidator.class)
@Documented
public @interface WhiteSpace {
    String message() default "{WhiteSpace.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
