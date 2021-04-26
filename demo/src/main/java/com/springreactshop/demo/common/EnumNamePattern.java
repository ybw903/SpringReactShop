package com.springreactshop.demo.common;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD,FIELD,ANNOTATION_TYPE,CONSTRUCTOR,PARAMETER,TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumNamePatterValidater.class)
public @interface EnumNamePattern {
    Class<? extends Enum<?>> enumClass();
    String message() default "must match \"{regexp}\"";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
