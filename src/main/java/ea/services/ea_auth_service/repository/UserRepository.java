package ea.services.ea_auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ea.services.ea_auth_service.repository.entity.UserEntity;


@Repository
public interface UserRepository  extends JpaRepository<UserEntity, String>{
	
	UserEntity findByMobileNumber(String mobileNumber);
	
}
