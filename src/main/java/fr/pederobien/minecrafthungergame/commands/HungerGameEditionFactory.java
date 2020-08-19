package fr.pederobien.minecrafthungergame.commands;

import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class HungerGameEditionFactory {

	/**
	 * @return An edition to create a new hunger game configuration.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> newHungerGame() {
		return new NewHungerGame();
	}

	/**
	 * @return An edition to rename an hunger game configuration.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> renameHungerGame() {
		return new RenameHungerGame();
	}

	/**
	 * @return An edition to save an hunger game configuration.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> saveHungerGame() {
		return new SaveHungerGame();
	}

	/**
	 * @return An edition to display the name of all registered hunger game configurations.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> listHungerGame() {
		return new ListHungerGame();
	}

	/**
	 * @return An edition to delete the file of an hunger game configuration.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> deleteHungerGame() {
		return new DeleteHungerGame();
	}

	/**
	 * @return An edition to display the characteristics of the current hunger game configuration.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> detailsHungerGame() {
		return new DetailsHungerGame();
	}

	/**
	 * @return An edition to load an hunger game configuration.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> loadHungerGame() {
		return new LoadHungerGame();
	}

	/**
	 * @return An edition to set the time after which players respawn in spectator mode.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> playerDontReviveTime() {
		return new PlayerDontReviveTime();
	}
}
