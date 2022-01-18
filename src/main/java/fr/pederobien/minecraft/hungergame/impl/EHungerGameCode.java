package fr.pederobien.minecraft.hungergame.impl;

import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;

public enum EHungerGameCode implements IMinecraftCode {
	// Common codes --------------------------------------------------------------

	// Code for the "true" value
	HUNGER_GAME__TRUE,

	// Code for the "false" value
	HUNGER_GAME__FALSE,

	// Code for the boolean bad format
	HUNGER_GAME__BOOLEAN_BAD_FORMAT,

	// Code for the integer bad format
	HUNGER_GAME__INTEGER_BAD_FORMAT,

	// Code for the "none" translation
	HUNGER_GAME_NONE,

	// Code for the name parameter
	HUNGER_GAME_NAME,

	// Code for the teams parameter
	HUNGER_GAME_TEAMS,

	// Code for the UHC parameter
	HUNGER_GAME_UHC,

	// Code for the player don't revive time
	HUNGER_GAME_PLAYER_DONT_REVIVE_TIME,

	// Code for the player don't revive time
	HUNGER_GAME_PVP_TIME,

	// Code for the item to give on player kills
	HUNGER_GAME_ITEM_ON_PLAYER_KILLS,

	// Code for the rules parameter
	HUNGER_GAME__RULES,

	// Code for the rule parameter
	HUNGER_GAME__RULE,

	// Code for the "hg" command -------------------------------------------------
	HUNGER_GAME__EXPLANATION,

	// Code for the "hg new" command ---------------------------------------------
	HUNGER_GAME__NEW_GAME__EXPLANATION,

	// Code when the name is missing while creating a new hunger game
	HUNGER_GAME__NEW_GAME__NAME_IS_MISSING,

	// Code when the name is already used
	HUNGER_GAME__NEW_GAME__NAME_ALREADY_USED,

	// Code when the hunger game is created
	HUNGER_GAME__NEW_GAME__GAME_CREATED,

	// Code for the "hg details" command -----------------------------------------
	HUNGER_GAME__DETAILS_GAME__EXPLANATION,

	// Code to display the details of an hunger game
	HUNGER_GAME__DETAILS_GAME__ON_DETAILS,

	// Code for the "hg rename" command ------------------------------------------
	HUNGER_GAME__RENAME_GAME__EXPLANATION,

	// Code when the hunger game name is missing
	HUNGER_GAME__RENAME_GAME__NAME_IS_MISSING,

	// Code when the hunger game name is already used
	HUNGER_GAME__RENAME_GAME__NAME_ALREADY_USED,

	// Code when the hunger game is renamed
	HUNGER_GAME__RENAME_GAME__GAME_RENAMED,

	// Code for the "hg delete" command ------------------------------------------
	HUNGER_GAME__DELETE_GAME__EXPLANATION,

	// Code when the file name to delete is missing
	HUNGER_GAME__DELETE_GAME__NAME_IS_MISSING,

	// Code when the file has been deleted
	HUNGER_GAME__DELETE_GAME__GAME_DELETED,

	// Code for the "hg load" command --------------------------------------------
	HUNGER_GAME__LOAD_GAME__EXPLANATION,

	// Code when the hunger game name to load is missing
	HUNGER_GAME__LOAD_GAME__NAME_IS_MISSING,

	// Code when the hunger game is loaded
	HUNGER_GAME__LOAD_GAME__GAME_LOADED,

	// Code for the "hg list" command --------------------------------------------
	HUNGER_GAME_LIST_GAME__EXPLANATION,

	// Code when there is no hunger game files
	HUNGER_GAME_LIST_GAME__NO_ELEMENT,

	// Code when there is one hunger game files
	HUNGER_GAME_LIST_GAME__ONE_ELEMENT,

	// Code when there is several hunger game files.
	HUNGER_GAME_LIST_GAME__SEVERAL_ELEMENTS,

	// Code for the "hg save" command --------------------------------------------
	HUNGER_GAME__SAVE_GAME__EXPLANATION,

	// Code when the game serialization fails
	HUNGER_GAME__SAVE_GAME__FAIL_TO_SAVE,

	// Code when the game is saved
	HUNGER_GAME__SAVE_GAME__GAME_SAVED,

	// Code for the "hg playerDontReviveTime" command ----------------------------
	HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__EXPLANATION,

	// Code when the time is missing
	HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__TIME_IS_MISSING,

	// Code when players don't revive from the beginning of the game
	HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__FROM_THE_BEGINNING,

	// Code when players don't revive after a certain time of play
	HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__TIME_UPDATED,

	// Code for the "hg UHC" command ---------------------------------------------
	HUNGER_GAME__UHC__EXPLANATION,

	// Code when the value is missing
	HUNGER_GAME__UHC__VALUE_IS_MISSING,

	// Code when the UHC is enabled
	HUNGER_GAME__UHC__UHC_ENABLED,

	// Code when the UHC is enabled
	HUNGER_GAME__UHC__UHC_DISABLED,

	// Code for the "hg itemOnPlayerKills" command -------------------------------
	HUNGER_GAME__ITEM_ON_PLAYER_KILLS__EXPLANATION,

	// Code for the amount completion
	HUNGER_GAME__ITEM_ON_PLAYER_KILLS__AMOUNT__COMPLETION,

	// Code when the material of the item stack is missing
	HUNGER_GAME__ITEM_ON_PLAYER_KILLS__ITEM_IS_MISSING,

	// Code when the material does not exist
	HUNGER_GAME__ITEM_ON_PLAYER_KILLS__ITEM_NOT_FOUND,

	// Code when the item stack is updated
	HUNGER_GAME__ITEM_ON_PLAYER_KILLS__ITEM_UPDATED,

	// Code for the start arguments ----------------------------------------------
	HUNGER_GAME__SKIP__EXPLANATION,

	// Code for in game messages--------------------------------------------------

	// Code when the overworld's border is missing
	HUNGER_GAME__OVERWORLD_BORDER_IS_MISSING,

	// Code when there is no resurrection
	HUNGER_GAME__NO_RESURRECTION(PlayerGroup.ALL),

	// Code when the "no resurrection" count down
	HUNGER_GAME_NO_RESURRECTION_COUNT_DOWN(PlayerGroup.ALL);

	private IPlayerGroup group;

	private EHungerGameCode() {
		this(PlayerGroup.OPERATORS);
	}

	private EHungerGameCode(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String value() {
		return name();
	}

	@Override
	public IPlayerGroup getGroup() {
		return group;
	}

	@Override
	public void setGroup(IPlayerGroup group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return String.format("value=%s,group=%s", value(), getGroup());
	}
}
