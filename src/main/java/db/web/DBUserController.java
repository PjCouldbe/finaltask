package db.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import db.model.User;
import db.repository.UserRepository;
import db.repository.impl.UserRepositoryImpl;
import db.validator.UserValidator;

@Controller
public class DBUserController {
	private static final Logger logger = LoggerFactory.getLogger(DBUserController.class);
	
	//***** DAO ***************************
	@Autowired
	private UserRepository userRep;
	
	public UserRepository getUserRep() {
		return userRep;
	}

	public void setUserRep(UserRepositoryImpl userRep) {
		this.userRep = userRep;
	}
	//*************************************
	
	private Validator validator = new UserValidator();
	
	@ModelAttribute("user")
    public User getUserObject() {
        return new User();
    }
	
	@RequestMapping(value = "/user_db/view.html", method = RequestMethod.GET)
    public String initializeUserList(ModelMap model) {
		model.addAttribute("userList", userRep.showAllUsers());
		logger.info("Showing user list");
        return "user_db/view";
    }
	
	@RequestMapping(value = "/user_db/edit.html", method = RequestMethod.POST)
	public String onSubmit(
            @ModelAttribute("user")User user,
                          BindingResult result,
                          ModelMap model
            ) {
    	validator.validate(user, result);
    	if (result.hasFieldErrors()) {
    		return "/user_db/edit.html";
    	}
		userRep.addUser(user);
        logger.info("Adding a user: " 
        		+ user.getData() + ": "
        		+ user.getAge() + " years");

        //redirectAttributes.addAttribute("flash", "Car was added: " + car.getBrand() + " for $" + 
        //car.getPrice() + "");
    	return "redirect:user_db/view.html";
    }
}