package fr.pederobien.minecrafthungergame.interfaces;

import fr.pederobien.minecraftgameplateform.interfaces.element.IGameObjective;

public interface IHungerGameObjective extends IGameObjective<IHungerGameConfiguration> {

	/**
	 * Display not empty teams and the number of player in game mode Survival.
	 */
	void addTeams();
}
