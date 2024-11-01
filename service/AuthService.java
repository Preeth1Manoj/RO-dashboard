package com.report.ro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.report.ro.dto.LoginRequest;
import com.report.ro.dto.UserDTO;
import com.report.ro.model.User;
import com.report.ro.repository.UserRepository;

import java.util.Optional;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;

	public UserDTO login(LoginRequest loginRequest) {
		Optional<User> userOpt = userRepository.findByUsername(loginRequest.getUsername());

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getPassword().equals(loginRequest.getPassword())) {
				UserDTO userDTO = new UserDTO();
				userDTO.setId(user.getId());
				userDTO.setUsername(user.getUsername());
				userDTO.setEmail(user.getEmail());
				userDTO.setRole(user.getRole());
				return userDTO;
			}
		}
		return null;
	}
}