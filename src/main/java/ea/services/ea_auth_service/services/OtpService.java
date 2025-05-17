package ea.services.ea_auth_service.services;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ea.services.ea_auth_service.repository.OtpRepository;
import ea.services.ea_auth_service.repository.entity.OtpEntity;

@Service
public class OtpService {

	@Autowired
	private OtpRepository otpRepository;
	
	public String generateOTP(String mobileNumber) {
		
		/** Pending Implementation
		 * - Fetch user from core-service synchronously using Feign Client.
		 * - If not found, Send a Kafka event to core-service to insert user into the user_master table of core-service.
		 * 		So that we will consider the mobile number is a new user and no need of registration.
		 * - If found, then generate OTP and save it to user_otp_master table of ea-auth-service.
		 */
		// Mark the previous OTP as inactive.
		OtpEntity otpDbEntity = otpRepository.findTopByMobileNumberOrderByExpiryDateTimeDesc(mobileNumber);		
		if(otpDbEntity!=null) {
			otpDbEntity.setIsActive(0);
			otpRepository.save(otpDbEntity);
		}
		
		// Now generate new OTP
        String otp = String.format("%06d", new Random().nextInt(999999));
        
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setMobileNumber(mobileNumber);
        otpEntity.setOtp(otp);
        otpEntity.setCreatedDateTime(LocalDateTime.now());
        otpEntity.setExpiryDateTime(LocalDateTime.now().plusMinutes(5)); // OTP valid for 5 mins
        otpEntity.setIsActive(1);
        
        otpRepository.save(otpEntity);

        sendOTPSMS(mobileNumber, otp);
        return otp;
    }

    private void sendOTPSMS(String mobileNumber, String otp) {
        // Integrate with an SMS provider like Twilio, Nexmo, or Firebase
        System.out.println("Sending OTP " + otp + " to " + mobileNumber);
    }
    
    public boolean verifyOTP(String mobileNumber, String otp) {
    	OtpEntity otpEntity = otpRepository.findTopByMobileNumberOrderByExpiryDateTimeDesc(mobileNumber);

        if (otpEntity != null && otpEntity.getOtp().equals(otp) && otpEntity.getExpiryDateTime().isAfter(LocalDateTime.now())) {
            return true;
        }
        return false;
    }

}
