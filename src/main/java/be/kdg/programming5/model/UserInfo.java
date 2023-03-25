package be.kdg.programming5.model;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

/**
 A Projection for the {@link User} entity
 */
public interface UserInfo {
	Long getUserID();

	String getName();

	String getPassword();

	LocalDate getBirthdate();

	Role getRole();

	@Value ("#{target.getPosts().size()}")
	int getPostCount();
}
