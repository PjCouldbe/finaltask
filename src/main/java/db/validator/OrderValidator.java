package db.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import db.model.Order;
import db.model.User;

public class OrderValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Order.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Order o = (Order)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "goods", "field.required", "Required field");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "totalAmount", "field.required", "Required field");
		if (!errors.hasFieldErrors("totalAmount")) {
			if (o.getTotalAmount() <= 0) {
				errors.rejectValue("totalAmount", "field.not_zero", "Can't be free!");
			}
		}
	}
}