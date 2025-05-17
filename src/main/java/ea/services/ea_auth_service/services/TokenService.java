package ea.services.ea_auth_service.services;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	// Store Token with Expiry (Using IP & Device Fingerprint)
	public void storeToken(String mobileNumber, String token, String deviceFingerprint) {
		String key = "TOKEN:" + mobileNumber + ":" + deviceFingerprint;
		redisTemplate.opsForValue().set(key, token, 15, TimeUnit.MINUTES); // Expiry matches JWT expiration
	}

	// Validate Token (Ensure it exists in Redis)
	public boolean isTokenValid(String mobileNumber, String token, String deviceFingerprint) {
		String key = "TOKEN:" + mobileNumber + ":" + deviceFingerprint;
		String storedToken = redisTemplate.opsForValue().get(key);
		return token.equals(storedToken);
	}

	// Invalidate Token (Remove from Redis)
	public void invalidateToken(String mobileNumber, String deviceFingerprint) {
		String key = "TOKEN:" + mobileNumber + ":" + deviceFingerprint;
		redisTemplate.delete(key);
	}
}
