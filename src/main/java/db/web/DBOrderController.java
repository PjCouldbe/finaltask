package db.web;

import java.util.LinkedList;
import java.util.List;

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
	
	@RequestMapping(value = "/order_db/view.html", method = RequestMethod.GET)
	public String initializeOrderList(ModelMap model) {   
		//model.addAttribute("orderList", orderRep.showAllOrders());
		List<OrderView> orderList = new LinkedList<>();
		for (Order o : orderRep.showAllOrders()) {
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
	
	@RequestMapping(value = "/order_db/edit.html", method = RequestMethod.GET)
	public String initializeForm (ModelMap model) {
		List<User> customerList = userRep.selectGroupUsers("customer");
		List<User> salerList = userRep.selectGroupUsers("saler");
		
		model.addAttribute("customerList", customerList);
		model.addAttribute("salerList", salerList);
    	
    	return "order_db/edit";
    }
	
	@RequestMapping(value = "/order_db/edit.html", method = RequestMethod.POST)
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

    	return "redirect:order_db/view.html";
    }
}