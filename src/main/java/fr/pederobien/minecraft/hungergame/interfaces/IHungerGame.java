package fr.pederobien.minecraft.hungergame.interfaces;

import fr.pederobien.minecraft.hungergame.interfaces.state.IStateGame;
import fr.pederobien.minecraftgameplateform.interfaces.element.IGame;

public interface IHungerGame extends IGame, IStateGame {

	/**
	 * @return The configuration associated to this game.
	 */
	IHungerGameConfiguration getConfiguration();
}
