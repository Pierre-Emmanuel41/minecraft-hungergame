package fr.pederobien.minecrafthungergame.interfaces;

import fr.pederobien.minecraftgameplateform.interfaces.element.IGame;
import fr.pederobien.minecrafthungergame.interfaces.state.IStateGame;

public interface IHungerGame extends IGame, IStateGame {

	IUnmodifiableHungerGameConfiguration getConfiguration();
}
