package ea.services.ea_auth_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ea.services.ea_auth_service.repository.entity.OtpEntity;

@Repository
public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
	OtpEntity findTopByMobileNumberOrderByExpiryDateTimeDesc(String mobileNumber);
}
