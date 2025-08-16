// Updated AuthController.java with test GET endpoints
package com.votingsystem.controller;

import com.votingsystem.dto.auth.LoginRequest;
import com.votingsystem.dto.auth.LoginResponse;
import com.votingsystem.service.CustomUserDetailsService;
import com.votingsystem.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // Test endpoint to verify server is working
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth controller is working! Use POST requests for login endpoints.");
    }

    // Test endpoints for browser access (GET methods)
    @GetMapping("/login")
    public ResponseEntity<String> loginInfo() {
        return ResponseEntity.ok("Login endpoint is working. Send POST request with JSON body: {\"username\": \"your_username\", \"password\": \"your_password\"}");
    }

    @GetMapping("/admin/login")
    public ResponseEntity<String> adminLoginInfo() {
        return ResponseEntity.ok("Admin login endpoint is working. Send POST request with JSON body: {\"username\": \"admin_username\", \"password\": \"admin_password\"}");
    }

    @GetMapping("/voter/login")
    public ResponseEntity<String> voterLoginInfo() {
        return ResponseEntity.ok("Voter login endpoint is working. Send POST request with JSON body: {\"username\": \"voter_username\", \"password\": \"voter_password\"}");
    }

    // Original POST endpoints for actual login functionality
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details and generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            final String jwt = jwtUtil.generateToken(userDetails);

            // Extract role from authorities
            String role = userDetails.getAuthorities().stream()
                    .findFirst()
                    .map(authority -> authority.getAuthority().replace("ROLE_", ""))
                    .orElse("VOTER");

            LoginResponse response = new LoginResponse(jwt, userDetails.getUsername(), role, "Login successful");
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            LoginResponse errorResponse = new LoginResponse("Invalid username or password", false);
            return ResponseEntity.status(401).body(errorResponse);
        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse("Login failed: " + e.getMessage(), false);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate admin credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details and verify admin role
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            // Check if user has ADMIN role
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                LoginResponse errorResponse = new LoginResponse("Access denied. Admin role required.", false);
                return ResponseEntity.status(403).body(errorResponse);
            }

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(userDetails);
            LoginResponse response = new LoginResponse(jwt, userDetails.getUsername(), "ADMIN", "Admin login successful");
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            LoginResponse errorResponse = new LoginResponse("Invalid admin credentials", false);
            return ResponseEntity.status(401).body(errorResponse);
        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse("Admin login failed: " + e.getMessage(), false);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }

    @PostMapping("/voter/login")
    public ResponseEntity<LoginResponse> voterLogin(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate voter credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details and verify voter role
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

            // Check if user has VOTER role
            boolean isVoter = userDetails.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_VOTER"));

            if (!isVoter) {
                LoginResponse errorResponse = new LoginResponse("Access denied. Voter role required.", false);
                return ResponseEntity.status(403).body(errorResponse);
            }

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(userDetails);
            LoginResponse response = new LoginResponse(jwt, userDetails.getUsername(), "VOTER", "Voter login successful");
            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            LoginResponse errorResponse = new LoginResponse("Invalid voter credentials", false);
            return ResponseEntity.status(401).body(errorResponse);
        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse("Voter login failed: " + e.getMessage(), false);
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}