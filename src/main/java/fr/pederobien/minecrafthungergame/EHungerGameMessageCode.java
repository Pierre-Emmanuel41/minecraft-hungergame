package fr.pederobien.minecrafthungergame;

import fr.pederobien.minecraftdictionary.impl.Permission;
import fr.pederobien.minecraftdictionary.interfaces.IMinecraftMessageCode;

public enum EHungerGameMessageCode implements IMinecraftMessageCode {
	HG_EXPLANATION,

	// Code for command new
	NEW_HG__EXPLANATION, NEW_HG__NAME_IS_MISSING, NEW_HG__NAME_ALREADY_TAKEN, NEW_HG__CONFIGURATION_CREATED,

	// Code for command rename
	RENAME_HG__EXPLANATION, RENAME_HG__NAME_IS_MISSING, RENAME_HG__NAME_ALREADY_TAKEN, RENAME_HG__CONFIGURATION_RENAMED,

	// Code for command save
	SAVE_HG__EXPLANATION, SAVE_HG__CONFIGURATION_SAVED,

	// Code for command list
	LIST_HG__EXPLANATION, LIST_HG__NO_REGISTERED_CONFIGURATION, LIST_HG__ONE_REGISTERED_CONFIGURATION, LIST_HG__SEVERAL_ELEMENTS,

	// Code for command delete
	DELETE_HG__EXPLANATION, DELETE_HG__NAME_IS_MISSING, DELETE_HG__DID_NOT_DELETE, DELETE_HG__CONFIGURATION_DELETED,

	// Code for command details
	DETAILS_HG__EXPLANATION, DETAILS_HG__ON_DETAILS,

	// Code for command load
	LOAD_HG__EXPLANATION, LOAD_HG__NAME_IS_MISSING, LOAD_HG__CONFIGURATION_LOADED,

	// Code for command playerDontRevive
	PLAYER_DONT_REVIVE_TIME__EXPLANATION, PLAYER_DONT_REVIVE_TIME__TIME_IS_MISSING, PLAYER_DONT_REVIVE_TIME__FROM_THE_BEGINNING, PLAYER_DONT_REVIVE_TIME__TIME_DEFINED,

	// Code for in game messages
	PVP_ENABLED(Permission.ALL), PLAYER_DONT_REVIVE(Permission.ALL), PLAYER_WILL_NOT_REVIVE_IN;

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
