package fr.pederobien.minecrafthungergame.interfaces;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import fr.pederobien.minecraftgameplateform.interfaces.element.IGame;
import fr.pederobien.minecrafthungergame.interfaces.state.IStateGame;

public interface IHungerGame extends IGame, IStateGame {

	/**
	 * @return The configuration associated to this game.
	 */
	IHungerGameConfiguration getConfiguration();

	/**
	 * Create an hunger game objective for the given player.
	 * 
	 * @param scoreboard The score board associated to the created objective.
	 * @param player     The player for which an hunger game objective is created.
	 */
	void createObjective(Scoreboard scoreboard, Player player);

	/**
	 * @return The list of all registered objectives. This list is unmodifiable.
	 */
	List<IHungerGameObjective> getObjectives();
}
