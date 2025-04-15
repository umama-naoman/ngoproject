package com.example.ngoproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;  

	    @Column(nullable = false)
	    private String username;

	    @Column(nullable = false, unique = true)
	    private String email;

	    @Column(nullable = false)
	    private String password;

	    @Column(nullable = false)
	    private String role = "USER";  // Default role for all new users

	    // Constructors
	    public User() {}

	    public User(String username, String email, String password) {
	        this.username = username;
	        this.email = email;
	        this.password = password;
	        this.role = "USER"; // Ensures all new users are assigned "USER"
	    }

	    // Getters and Setters
	    public Long getId() {
	        return id;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }

	    public String getRole() {
	        return role;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }
	}

