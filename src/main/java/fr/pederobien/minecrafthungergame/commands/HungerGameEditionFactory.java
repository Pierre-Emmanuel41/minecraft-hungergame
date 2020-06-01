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
}
