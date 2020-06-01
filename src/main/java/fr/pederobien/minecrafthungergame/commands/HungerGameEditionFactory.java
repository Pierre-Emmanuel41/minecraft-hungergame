package fr.pederobien.minecrafthungergame.commands;

import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class HungerGameEditionFactory {

	/**
	 * @return An edition to create a new hunger game style.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> newHungerGame() {
		return new NewHungerGame();
	}

	/**
	 * @return An edition to rename an hunger game style.
	 */
	public static IMapPersistenceEdition<IHungerGameConfiguration> renameHungerGame() {
		return new RenameHungerGame();
	}
}
