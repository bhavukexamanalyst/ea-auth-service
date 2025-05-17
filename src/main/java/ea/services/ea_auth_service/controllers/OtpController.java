package ea.services.ea_auth_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ea.services.ea_auth_service.controllers.dto.AuthResponse;
import ea.services.ea_auth_service.services.OtpService;
import ea.services.ea_auth_service.services.TokenService;
import ea.services.ea_auth_service.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/otp")
public class OtpController {

	@Autowired
	private OtpService otpService;
	
	@Autowired
    private TokenService tokenService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/generate")
    public String generateOTP(@RequestParam String mobileNumber) {
        return otpService.generateOTP(mobileNumber);
    }

    @PostMapping("/verify")
    public ResponseEntity<AuthResponse>  verifyOTP(@RequestParam String mobileNumber, @RequestParam String otp, HttpServletRequest httpRequest) {
    	 boolean isValid = otpService.verifyOTP(mobileNumber, otp);
         if (isValid) {
        	 String token =  jwtUtil.generateToken(mobileNumber);
             
        	 // Store token with device fingerprint
             String deviceFingerprint = httpRequest.getHeader("User-Agent");
             tokenService.storeToken(mobileNumber, token, deviceFingerprint);
             
             return ResponseEntity.ok(new AuthResponse(token));
         }
         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }
}
