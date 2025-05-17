package ea.services.ea_auth_service.kafka.model;

public class UserEvent {
	private String userId;
	private String action;
	private String timestamp;

	// Constructors
	public UserEvent() {
	}

	public UserEvent(String userId, String action, String timestamp) {
		this.userId = userId;
		this.action = action;
		this.timestamp = timestamp;
	}

	// Getters & Setters
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
