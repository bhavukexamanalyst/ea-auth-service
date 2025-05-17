package ea.services.ea_auth_service.repository.entity;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "user_master")
@Data
public class UserEntity {
	
	@Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private String id;
    
	@Id
	@Column(name = "mobile_number", nullable = false)
    private String mobileNumber;
	
    public UserEntity() {
        this.id = UUID.randomUUID().toString(); // Generate UUID on creation
    }

    public UserEntity(String name) {
        this.id = UUID.randomUUID().toString();
        this.mobileNumber = name;
    }
}
