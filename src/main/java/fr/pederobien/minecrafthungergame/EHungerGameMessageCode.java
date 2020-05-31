package fr.pederobien.minecrafthungergame;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EHungerGameMessageCode implements IMinecraftMessageCode {
	PVP_ENABLED(Permission.ALL), PLAYER_DONT_REVIVE(Permission.ALL);

	private Permission permission;

	private EHungerGameMessageCode() {
		this(Permission.OPERATORS);
	}

	private EHungerGameMessageCode(Permission permission) {
		this.permission = permission;
	}

	@Override
	public String value() {
		return toString();
	}

	@Override
	public Permission getPermission() {
		return permission;
	}

	@Override
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}
