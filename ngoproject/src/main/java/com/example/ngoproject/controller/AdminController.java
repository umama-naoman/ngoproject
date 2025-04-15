package com.example.ngoproject.controller;

import com.example.ngoproject.model.Cards;
import com.example.ngoproject.model.User;
import com.example.ngoproject.services.CardsService;
import com.example.ngoproject.services.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CardsService cardService;  
    
    @Autowired
    private UserService userService;

    private final String uploadDir = "src/main/resources/static/uploads/";

    //Admin Dashboard
    @GetMapping("")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
      if(userDetails == null || !userDetails.getAuthorities().stream()
    		  .anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
    	  return "redirect:/login?errorMessage=Access Denied";
      }	 
	 model.addAttribute("username", userDetails.getUsername());
        return "admin/adminDash";
    }
    
    /*
	 * public String showDashboard(HttpSession session, Model model) { if
	 * (session.getAttribute("loggedInUser") == null) { // User is not logged in
	 * return
	 * "redirect:/login?errorMessage=You must log in to access the Admin Dashboard."
	 * ; } return "admin/adminDash"; }
	 */
    
    @GetMapping("/users")
    public String listUsers(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null || !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/login?errorMessage=Access Denied";
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/makeAdmin"; //Admin page to list all users
    }

    @PostMapping("/admin/make-admin")
    public String makeAdmin(@RequestParam String email, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null || !userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            return "redirect:/login?errorMessage=Access Denied";
        }
        userService.makeAdmin(email);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/make-admin")
    public String makeAdmin(@RequestParam String email, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null || !loggedInUser.getRole().equals("admin")) {
            return "redirect:/login?errorMessage=Access Denied";
        }

        userService.makeAdmin(email);
        return "redirect:/admin/users";
    }

    //to allow only admins to access the dashboard
//    @GetMapping("/admin/dashboard")
//    public String showAdminDashboard(HttpSession session, Model model) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//
//        if (loggedInUser == null || !loggedInUser.getRole().equals("admin")) {
//            return "redirect:/login?errorMessage=Access Denied";
//        }
//
//        model.addAttribute("user", loggedInUser);
//        return "adminDashboard"; // Your admin dashboard HTML
//    }

    
    // IMAGE MANAGEMENT
    
    // Upload Hero section image
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam MultipartFile file) throws IOException {
    	File uploadFolder = new File(uploadDir);
    	if (!file.isEmpty()) {
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs(); // Create directory if it doesn't exist
            }

            Path filePath = Paths.get(uploadDir + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
        }
        return "redirect:/admin/uploaded-images";
    }


    // Display Uploaded Images
    @GetMapping("/uploaded-images")
    public String viewUploadedImages(Model model) {
        File folder = new File(uploadDir);
        List<String> images = Arrays.stream(folder.listFiles())
                .map(File::getName)
                .collect(Collectors.toList());
        model.addAttribute("images", images);
        return "admin/uploaded_imgs";
    }

    // Delete an Image
    @PostMapping("/delete")
    public String deleteImage(@RequestParam String filename) {
        File file = new File(uploadDir + filename);
        if (file.exists()) {
            file.delete();
        }
        return "redirect:/admin/uploaded-images";
    }

    @GetMapping("/home_img")
    public String uploadPage() {
        return "admin/homeimg_upload";
    }

    // CARDS MANAGEMENT LOGIC

    // Load Manage Cards Page with all cards
    @GetMapping("/cards")
    public String manageCards(Model model) {
        model.addAttribute("cards", cardService.getAllCards());
        return "admin/cards"; 
    }

    // Add a new card
    @PostMapping("/cards/add")
    @ResponseBody
    public Cards addCard(@RequestBody Cards card) {
        return cardService.saveCard(card);
    }

    // Delete a card
    @DeleteMapping("/cards/{id}")
    @ResponseBody
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
