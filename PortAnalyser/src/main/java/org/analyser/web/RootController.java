package org.analyser.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RootController {

	@RequestMapping(value = "/")
	public String defaulPage() {
		return "redirect:/index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login_v1";
	}
	
	@RequestMapping(value = "/index")
	public String index() {
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value = "403")
	public String accessDenied() {
		return "403";
	}
}
