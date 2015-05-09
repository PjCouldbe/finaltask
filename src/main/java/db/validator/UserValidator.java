package db.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import db.model.User;

public class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User u = (User)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "field.required", "Required field");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "middlename", "field.required", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "field.required", "Required field");
		if (!errors.hasFieldErrors("age")) {
			if (u.getAge() <= 0) {
				errors.rejectValue("age", "field.not_zero", "Can't be free!");
			}
			if (u.getAge() >= 300) {
				errors.rejectValue("age", "field.overflow", "Too large value!");
			}
		}
	}
}