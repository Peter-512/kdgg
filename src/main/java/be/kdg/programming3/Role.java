package be.kdg.programming3;

import java.util.Random;

public enum Role {
	Admin,
	Mod,
	User;

	public static Role randomRole() {
		Random random = new Random();
		return Role.values()[random.nextInt(Role.values().length)];
	}
}
