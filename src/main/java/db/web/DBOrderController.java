package db.web;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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

import db.model.Order;
import db.model.OrderView;
import db.model.User;
import db.repository.OrderRepository;
import db.repository.UserRepository;
import db.validator.OrderValidator;

@Controller
public class DBOrderController {
	private static final Logger logger = LoggerFactory.getLogger(DBOrderController.class);
	
	//***** DAO ***************************
	@Autowired
	private UserRepository userRep;
	
	public UserRepository getUserRep() {
		return userRep;
	}

	public void setUserRep(UserRepository userRep) {
		this.userRep = userRep;
	}
	
	@Autowired
	private OrderRepository orderRep;
	
	public OrderRepository getOrderRep() {
		return orderRep;
	}
	
	public void setOrderRep(OrderRepository orderRep) {
		this.orderRep = orderRep;
	}
	//*************************************
	
	private Validator validator = new OrderValidator();
	
	@ModelAttribute("order")
    public Order getOrderObject() {
        return new Order();
    }
	
	@RequestMapping(value = "/order_db/view", method = RequestMethod.GET)
	public String initializeOrderList(ModelMap model) {   
		//model.addAttribute("orderList", orderRep.showAllOrders());
		List<OrderView> orderList = new LinkedList<>();
		for (Order o : orderRep.showAllOrders()) {
			System.out.println(o.toString());
			orderList.add(new OrderView(o.getOrderId(), 
										userRep.selectUser(o.getCustomerId()).getData(), 
										userRep.selectUser(o.getSalesPersonId()).getData(), 
										o.getGoods(), 
										o.getTotalAmount()));
		}
		model.addAttribute("orderList", orderList);
		
		logger.info("Showing order list");
		return "/order_db/view";
	}
	
	@RequestMapping(value = "/order_db/edit", method = RequestMethod.GET)
	public String initializeForm (ModelMap model) {
		List<User> customerList = userRep.selectGroupUsers("customer");
		List<User> salerList = userRep.selectGroupUsers("saler");
		
		model.addAttribute("customerList", customerList);
		model.addAttribute("salerList", salerList);
    	
    	return "order_db/edit";
    }
	
	@RequestMapping(value = "/order_db/delete", method = RequestMethod.GET)
    public String deleteUser(ModelMap model, HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		orderRep.delete(id);
		return "redirect:view.html";
    }
	
	@RequestMapping(value = "/order_db/edit", method = RequestMethod.POST)
	public String onSubmit(
            @ModelAttribute("order")Order order,
                          BindingResult result,
                          ModelMap model
            ) {
    	validator.validate(order, result);
    	if (result.hasFieldErrors("goods") || result.hasFieldErrors("totalAmount")) {
    		return initializeForm(model);
    	}
		orderRep.addOrder(order);
        logger.info("Adding an order: " 
        		+ order.getCustomerId() + " " 
        		+ order.getSalesPersonId() + ": "
        		+ order.getGoods() + " - "
        		+ order.getTotalAmount());

    	return "redirect:view.html";
    }
	
	@RequestMapping(value = "/order_db/update", method = RequestMethod.GET)
    public ModelAndView initializeChangeForm(HttpServletRequest request) throws SQLException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Order o = orderRep.selectOrder(id);
		logger.info(o.toString());
		ModelAndView model = new ModelAndView("order_db/edit");
		
		List<User> customerList = userRep.selectGroupUsers("customer");
		List<User> salerList = userRep.selectGroupUsers("saler");
		
		model.addObject("customerList", customerList);
		model.addObject("salerList", salerList);
		model.addObject("order", o);
        return model;
	}
	
	@RequestMapping(value = "/order_db/update", method = RequestMethod.POST)
	public String onUpdate(@ModelAttribute("order") Order o, 
			HttpServletRequest request, BindingResult result, ModelMap model) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		validator.validate(o, result);
    	if (result.hasFieldErrors()) {
    		return "order_db/edit";
    	}
		orderRep.update(o, id);
        logger.info("Adding an order: " + o.toString());

        //redirectAttributes.addAttribute("flash", "Car was added: " + car.getBrand() + " for $" + 
        //car.getPrice() + "");
    	return "redirect:view.html";
    }
}