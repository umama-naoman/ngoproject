package com.example.ngoproject.services;

import com.example.ngoproject.model.User;
import com.example.ngoproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(username);
		return user.map(u -> org.springframework.security.core.userdetails.User
				.withUsername(u.getUsername())
				.password(u.getPassword())
				.authorities(u.getRole().toUpperCase())
				.build())
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

    public void saveUser(String username, String password, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		userRepository.save(user);
	}

    //User registration
    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }
        //hash password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void save(User user) {
		userRepository.save(user);
		
	}

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
	
	public void makeAdmin(String email) {
	    Optional<User> userOptional = userRepository.findByEmail(email);
	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        if(!"ADMIN".equals(user.getRole())) {
	        user.setRole("ADMIN");
	        userRepository.save(user);
	        }
	    }
	}

}


