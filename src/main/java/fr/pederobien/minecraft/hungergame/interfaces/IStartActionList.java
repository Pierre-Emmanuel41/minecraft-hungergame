package fr.pederobien.minecraft.hungergame.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.GameMode;
import org.bukkit.GameRule;

public interface IStartActionList {

	/**
	 * Set if a specific action should be executed before starting a game.
	 * 
	 * @param startAction The action to execute or not.
	 * @param value       True to execute the action, false to not execute the action.
	 */
	void set(StartAction startAction, boolean value);

	/**
	 * Performs actions before starting the hunger game. Some action may be skip using {@link #set(StartAction, boolean)}.
	 */
	void start();

	/**
	 * For each action from the {@link StartAction} enumeration, it will reset the associated value. In other words, each action will
	 * be executed the next time the method {@link #start()} is called.
	 */
	void reset();

	public enum StartAction {
		/**
		 * Action to initialize the world border according to border properties.
		 */
		INITIALIZE_BORDERS("initializeBorders"),

		/**
		 * Action to give resistance, regeneration and saturation to each player
		 */
		GIVE_EFFECT("giveEffects"),

		/**
		 * Action to set the food level to 20 before starting a game.
		 */
		FOOD_LEVEL("foodLevel"),

		/**
		 * Action to set the max health to 20 before starting a game.
		 */
		MAX_HEALTH("maxHealth"),

		/**
		 * Action to set the health to 20 before starting a game.
		 */
		HEALTH("health"),

		/**
		 * Action to clean the inventory before starting a game.
		 */
		CLEAN_INVENTORY("cleanInventory"),

		/**
		 * Action to set the experience level to 0 before starting a game.
		 */
		EXP_LEVEL("expLevel"),

		/**
		 * Action to set the game mode to {@link GameMode#SURVIVAL}.
		 */
		GAME_MODE("gameMode"),

		/**
		 * Action to create the teams on the server.
		 */
		CREATE_SERVER_TEAM("createServerTeam"),

		/**
		 * Action to teleport randomly teams before starting a game.
		 */
		TELEPORT("teleport"),

		/**
		 * Action to set the time to day before starting a game.
		 */
		SET_DAY("setDay"),

		/**
		 * Action to clear the weather before starting a game.
		 */
		WEATHER_CLEAR("weatherClear"),

		/**
		 * Action to set the game rule {@link GameRule#DO_DAYLIGHT_CYCLE} to true.
		 */
		DO_DAY_LIGHT_CYCLE("doDayLightCycle"),

		/**
		 * Action to set the game rule {@link GameRule#DO_FIRE_TICK} to false.
		 */
		DO_FIRE_TICK("doFireTick"),

		/**
		 * Action to set the game rule {@link GameRule#NATURAL_REGENERATION} to true.
		 */
		NATURAL_REGENERATION("naturalRegeneration");

		private static final Map<String, StartAction> BY_NAME;

		static {
			BY_NAME = new HashMap<String, StartAction>();
			for (StartAction startAction : values())
				BY_NAME.put(startAction.getName(), startAction);
		}

		private String name;

		private StartAction(String name) {
			this.name = name;
		}

		/**
		 * Get the action associated to the given action name.
		 * 
		 * @param name The name of the action to retrieve.
		 * 
		 * @return An optional that contains the action if registered, an empty optional otherwise.
		 */
		public static Optional<StartAction> getByName(String name) {
			return Optional.ofNullable(BY_NAME.get(name));
		}

		/**
		 * @return The user friendly name of the action.
		 */
		public String getName() {
			return name;
		}
	}
}
