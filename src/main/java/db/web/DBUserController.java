package db.web;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.ModelAndView;

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
	
	@RequestMapping(value = "/user_db/view", method = RequestMethod.GET)
    public String initializeUserList(ModelMap model) {
		model.addAttribute("userList", userRep.showAllUsers());
		logger.info("Showing user list");
        return "user_db/view";
    }
	
	@RequestMapping(value = "/user_db/edit", method = RequestMethod.GET)
    public String initializeForm(ModelMap model) {
		logger.info("Showing user form");
        return "user_db/edit";
    }
	
	/*@RequestMapping(value = "/user_db/update", method = RequestMethod.GET)
    public String updateUser(ModelMap model, HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		User user = userRep.selectUser(id);
		model.addAttribute("user", user);
        return initializeForm(model);
    }*/
	
	@RequestMapping(value = "/user_db/delete", method = RequestMethod.GET)
    public String deleteUser(ModelMap model, HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		userRep.delete(id);
		return "redirect:view.html";
    }
	
	@RequestMapping(value = "/user_db/edit", method = RequestMethod.POST)
	public String onSubmit(
            @ModelAttribute("user")User user,
                          BindingResult result,
                          ModelMap model
            ) {
    	validator.validate(user, result);
    	if (result.hasFieldErrors()) {
    		return "user_db/edit";
    	}
		userRep.addUser(user);
        logger.info("Adding a user: " 
        		+ user.getData() + ": "
        		+ user.getAge() + " years");

        //redirectAttributes.addAttribute("flash", "Car was added: " + car.getBrand() + " for $" + 
        //car.getPrice() + "");
    	return "redirect:view.html";
    }
	
	@RequestMapping(value = "/user_db/update", method = RequestMethod.GET)
    public String initializeChangeForm(HttpServletRequest request, ModelMap model) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		User u = userRep.selectUser(id);
		model.addAttribute("user", u);
        return "user_db/update";
    }
	
	@RequestMapping(value = "/user_db/update", method = RequestMethod.POST)
	public String onUpdate(@ModelAttribute("user")User u, 
			HttpServletRequest request, BindingResult result, ModelMap model) {
		Integer id = Integer.parseInt(request.getParameter("id"));
				validator.validate(u, result);
    	if (result.hasFieldErrors()) {
    		return "user_db/update";
    	}
		userRep.update(u, u.getId());
        logger.info("Adding a user: " 
        		+ u.getData() + ": "
        		+ u.getAge() + " years");

        //redirectAttributes.addAttribute("flash", "Car was added: " + car.getBrand() + " for $" + 
        //car.getPrice() + "");
    	return "redirect:view.html";
    }
}