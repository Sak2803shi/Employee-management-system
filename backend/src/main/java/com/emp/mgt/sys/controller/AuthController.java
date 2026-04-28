package com.emp.mgt.sys.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.mgt.sys.dto.AuthResponse;
import com.emp.mgt.sys.dto.ChangePasswordRequest;
import com.emp.mgt.sys.dto.LoginRequest;
import com.emp.mgt.sys.dto.RegisterRequest;
import com.emp.mgt.sys.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins="*")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
//	register endpoint
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
		 return ResponseEntity.ok(authService.register(request));
	}
	
	// Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestBody Map<String, Object> body) {
        System.out.println("Raw body: " + body);
        return ResponseEntity.ok(body.toString());
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestBody ChangePasswordRequest request) {
        return ResponseEntity.ok(authService.changePassword(
                request.getUsername(),
                request.getCurrentPassword(),
                request.getNewPassword()
        ));
    }
    
    
}
