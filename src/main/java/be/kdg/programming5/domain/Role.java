package be.kdg.programming5.domain;

import java.util.Random;

public enum Role {
	User,
	Mod,
	Admin;

	public static Role randomRole() {
		Random random = new Random();
		return Role.values()[random.nextInt(Role.values().length)];
	}
}
