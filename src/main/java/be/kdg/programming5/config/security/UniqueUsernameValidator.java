package be.kdg.programming5.config.security;

import be.kdg.programming5.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsernameConstraint, String> {

	private final UserRepository userRepository;

	@Autowired
	public UniqueUsernameValidator(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void initialize(UniqueUsernameConstraint constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		return !userRepository.existsByName(username);
	}
}
