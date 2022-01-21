package fr.pederobien.minecraft.hungergame.interfaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.bukkit.GameMode;

public interface IStopActionList {

	/**
	 * Set if a specific action should be executed before stopping a game.
	 * 
	 * @param stopAction The action to execute or not.
	 * @param value      True to execute the action, false to not execute the action.
	 */
	void set(StopAction stopAction, boolean value);

	/**
	 * Performs actions before stopping the hunger game. Some action may be skip using {@link #set(StopAction, boolean)}.
	 */
	public void stop();

	/**
	 * For each action from the {@link StopAction} enumeration, it will reset the associated value. In other words, each action will
	 * be executed the next time the method {@link #stop()} is called.
	 */
	void reset();

	public enum StopAction {
		/**
		 * Action to reset the borders
		 */
		RESET_BORDERS("resetBorders"),

		/**
		 * Action delete the game teams from the server.
		 */
		DELETE_SERVER_TEAM("deleteServerTeam"),

		/**
		 * Action to set the game mode to {@link GameMode#CREATIVE}
		 */
		GAME_MODE("gameMode");

		private static final Map<String, StopAction> BY_NAME;

		static {
			BY_NAME = new HashMap<String, StopAction>();
			for (StopAction stopAction : values())
				BY_NAME.put(stopAction.getName(), stopAction);
		}

		private String name;

		private StopAction(String name) {
			this.name = name;
		}

		/**
		 * Get the action associated to the given action name.
		 * 
		 * @param name The name of the action to retrieve.
		 * 
		 * @return An optional that contains the action if registered, an empty optional otherwise.
		 */
		public static Optional<StopAction> getByName(String name) {
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
