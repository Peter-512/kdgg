package be.kdg.programming5.model;

import lombok.Getter;

@Getter
public enum Role {
	USER("ROLE_USER"),
	MOD("ROLE_MOD"),
	ADMIN("ROLE_ADMIN");

	private final String code;
	private final String description;

	Role(String code) {
		this.code = code;
		this.description = name().substring(0, 1).toUpperCase() + name().toLowerCase().substring(1);
	}
}
