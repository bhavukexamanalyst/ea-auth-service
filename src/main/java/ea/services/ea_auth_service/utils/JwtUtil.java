package ea.services.ea_auth_service.utils;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ea.services.ea_auth_service.repository.UserRepository;
import ea.services.ea_auth_service.repository.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    
	@Autowired
	private UserRepository userOtpRepository;
	
	private String secretKey = "";

    public JwtUtil() {
        generateSecretKey();
    }

    private void generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    public String generateToken(String mobileNumber) {
        Map<String, Object> claims = new HashMap<>();
        
        // Create a new user If not exists.
        UserEntity dbUser = userOtpRepository.findByMobileNumber(mobileNumber);
        if(null == dbUser) {
        	 UserEntity user  = new UserEntity(mobileNumber);
        	 userOtpRepository.save(user);
             System.out.println("New User Signed In with Mobile Number: "+ mobileNumber);
        }else {
        	System.out.println("User Already Exists. !!");
        }
       

        return Jwts.builder()
             .claims()
             .add(claims)
             .subject(mobileNumber)
             .issuedAt(new Date(System.currentTimeMillis()))
             .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
             .and()
             .signWith(getKey())
             .compact();
    }
    
    public boolean validateToken(String token, String mobileNumber) {
        String extractedMobileNumber = extractMobileNumber(token);
        return (extractedMobileNumber.equals(mobileNumber) && !isTokenExpired(token));
    }
    
    public String extractMobileNumber(String jwtToken) {
	    return extractClaim(jwtToken, Claims::getSubject);
	}
    
    public Date extractExpiration(String token) {
	    return extractClaim(token, Claims::getExpiration);
	}
    
	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	     final Claims claims = extractAllClaims(token);
	     return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
	     return  Jwts.parser()
	             .verifyWith(getKey())
	             .build()
	             .parseSignedClaims(token)
	             .getPayload();
	}
	
	private boolean isTokenExpired(String token) {
	    return extractExpiration(token).before(new Date());
	}

}
