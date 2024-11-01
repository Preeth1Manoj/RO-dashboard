package com.report.ro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.report.ro.dto.LoginRequest;
import com.report.ro.dto.UserDTO;
import com.report.ro.dto.response.ApiResponse;
import com.report.ro.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
		UserDTO userDTO = authService.login(loginRequest);
		if (userDTO != null) {
			return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", userDTO));
		}
		return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid credentials", null));
	}
}