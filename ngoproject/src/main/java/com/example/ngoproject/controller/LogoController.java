package com.example.ngoproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
public class LogoController {

    private static final String LOGO_DIRECTORY = "src/main/resources/static/uploads/logos/";
    private static final String LOGO_FILENAME = "logo.png"; // Fixed filename to keep only one logo

    @GetMapping("/admin/logo")
    public String showLogoPage(Model model) {
        File logoFile = new File(LOGO_DIRECTORY + LOGO_FILENAME);
        if (logoFile.exists()) {
            model.addAttribute("logoFilename", LOGO_FILENAME);
        }
        return "admin/logo";
    }

    @PostMapping("/admin/logo")
    public String uploadLogo(@RequestParam MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "admin/logo";
        }

        try {
            File uploadDir = new File(LOGO_DIRECTORY);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            Path filePath = new File(LOGO_DIRECTORY + LOGO_FILENAME).toPath();
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            model.addAttribute("message", "Logo uploaded successfully!");
            model.addAttribute("logoFilename", LOGO_FILENAME);
        } catch (IOException e) {
            model.addAttribute("message", "Error uploading file: " + e.getMessage());
        }

        return "admin/logo";
    }
}
