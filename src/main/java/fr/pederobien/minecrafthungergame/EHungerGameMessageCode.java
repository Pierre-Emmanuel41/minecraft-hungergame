package fr.pederobien.minecrafthungergame;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EHungerGameMessageCode implements IMinecraftMessageCode {
	HG_EXPLANATION,

	// Code for command new
	NEW_HG__EXPLANATION, NEW_HG__NAME_IS_MISSING, NEW_HG__NAME_ALREADY_TAKEN, NEW_HG__STYLE_CREATED,

	// Code for command rename
	RENAME_HG__EXPLANATION, RENAME_HG__NAME_IS_MISSING, RENAME_HG__NAME_ALREADY_TAKEN, RENAME_HG__STYLE_RENAMED,

	// Code for in game messages
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
