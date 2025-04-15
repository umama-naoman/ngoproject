package com.example.ngoproject.controller;

import com.example.ngoproject.model.User;
import com.example.ngoproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Show Registration Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";  
    }

    // Handle User Registration
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        try {
            userService.registerUser(user);
            model.addAttribute("successMessage", "User registered successfully!");
            return "login";  // Redirect to login page after successful registration
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";  // Stay on register page if there's an error
        }
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "login"; // Render login.html
    }

        @GetMapping("/forgot-password")
        public String showForgotPasswordPage() {
            return "forgotpassword"; // This is the page where the user enters their email.
        }

        @PostMapping("/forgot-password")
        public String processForgotPassword(@RequestParam String email, Model model) {
            Optional<User> user = userService.findByEmail(email);

            if (user.isPresent()) {
                //email sending logic for direct link to email.
                return "redirect:/reset-password?email=" + email;
            } else {
                model.addAttribute("error", "Email not found!");
                return "forgotpassword"; 
            }
        }

       @GetMapping("/reset-password")
       public String showResetPasswordPage(@RequestParam String email, Model model) {
         model.addAttribute("email", email);
         return "resetpassword"; // Show the reset password form.
       }

       @PostMapping("/reset-password")
       public String processResetPassword(@RequestParam String email,
                                          @RequestParam String newPassword,
                                          @RequestParam String confirmPassword, 
                                          Model model) {
           System.out.println("[Debug] Reset Password for Email: " + email); // Debugging message

           if (newPassword.equals(confirmPassword)) {
              Optional<User> user = userService.findByEmail(email);

              System.out.println("[DEBUG] Reset Password - User found: " + user.isPresent());  //Debugging message

               if (user.isPresent()) {
                   // Encrypt password before saving
                   user.get().setPassword(passwordEncoder.encode(newPassword));
                   userService.save(user.get());

                   System.out.println("Password reset successful! Redirecting to login...");
                   return "redirect:/login"; // Redirect to login page after password reset.
               } else {
                   System.out.println("Email not found in database. Redirecting...");
                   model.addAttribute("errorMessage", "Email not found!");
                   return "forgotpassword";
                   
               }
           } else {
               System.out.println("Passwords do not match! Redirecting...");
               model.addAttribute("email", email);
               model.addAttribute("errorMessage", "Passwords do not match!");
               return "resetpassword";

           }
       }

 }

