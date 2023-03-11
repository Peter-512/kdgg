package be.kdg.programming5.config.security;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint (validatedBy = {UniqueUsernameValidator.class})
@Target ({ElementType.FIELD})
@Retention (RetentionPolicy.RUNTIME)
public @interface UniqueUsernameConstraint {
	String message() default "User with this username already exists";

	Class[] groups() default {};

	Class[] payload() default {};
}
