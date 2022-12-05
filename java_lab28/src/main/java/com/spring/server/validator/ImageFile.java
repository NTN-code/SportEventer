package com.spring.server.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageFileValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageFile {
    String message() default "Невалидный файл - выберите .jpg";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
