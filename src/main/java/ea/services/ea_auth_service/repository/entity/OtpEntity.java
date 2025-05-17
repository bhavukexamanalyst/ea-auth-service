package ea.services.ea_auth_service.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "user_otp_master")
@Data
public class OtpEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
	
	@Column(name = "otp" , nullable = false)
    private String otp;
	
	@Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;
	
	@Column(name = "expiry_date_time" , nullable = false)
    private LocalDateTime expiryDateTime;
	
	@Column(name = "is_active" , nullable = false)
    private Integer isActive;
}
