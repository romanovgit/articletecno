package com.hampcode.articlestec.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping
public class LoginController {

	@GetMapping(value= {"/login"})
	public String login(@RequestParam(value="error", required = false) String error,
			@RequestParam(value="logout", required = false) String logout,
			Model model, Principal principal,
			RedirectAttributes flash
			) {
		
		//redirect: <url>		
		if(principal!=null) {
			return "redirect:/articles/allArticles";
		}
		
		if(error!=null) {
			model.addAttribute("error","username o password incorrectos, por favor vuelva a intentarlo!");
		}
		
		if(logout!=null) {
			model.addAttribute("success","Ha cerrado sesión con exito");
		}
		
		return "login";//VIEW
		
	}
}



