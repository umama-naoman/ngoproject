package com.example.ngoproject.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	    @GetMapping("/")
	    public String home() {
	        return "home";
	    }
	    
	    @GetMapping("/about")
	    public String aboutUs() {
	        return "about";
	    }

	    @GetMapping("/programs")
	    public String programs() {
	        return "programs";
	    }

	    @GetMapping("/campaigns")
	    public String campaigns() {
	        return "campaign";
	    }

	    @GetMapping("/resources")
	    public String resources() {
	        return "resources";
	    }

	    @GetMapping("/get-involved")
	    public String getInvolved() {
	        return "getinvolved";
	    }

	    @GetMapping("/contact")
	    public String contact() {
	        return "contact";
	    }


	     @GetMapping("/donations")
		 public String showDonationsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		     if (userDetails == null) {  
		         return "redirect:/login?errorMessage=You must log in to make a donation.";
		     }
		     model.addAttribute("username", userDetails.getUsername());  // Pass the user name to the page
		     return "donations";
		 }
	  

}




