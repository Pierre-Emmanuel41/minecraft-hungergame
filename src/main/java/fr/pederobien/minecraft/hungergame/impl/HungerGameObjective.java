package fr.pederobien.minecraft.hungergame.impl;

import java.util.function.Function;

import org.bukkit.entity.Player;

import fr.pederobien.minecraft.border.entries.BorderCenterEntry;
import fr.pederobien.minecraft.border.entries.BorderLocationEntry;
import fr.pederobien.minecraft.border.entries.BorderSizeCountDownEntry;
import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.WorldManager;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.minecraft.platform.entries.updaters.TimeTaskObserverEntryUpdater;
import fr.pederobien.minecraft.scoreboards.impl.Objective;
import fr.pederobien.minecraft.scoreboards.impl.updaters.UpdatersFactory;
import fr.pederobien.minecraft.scoreboards.interfaces.IEntry;

public class HungerGameObjective {
	private Objective objective;
	private BorderMoveTimeLineObserver borderMoveObserver;

	/**
	 * Creates an objective associated to the specified game in order to be displayed on the given player's screen.
	 * 
	 * @param game   The game associated to this objective.
	 * @param player The player associated to this objective.
	 */
	public HungerGameObjective(IHungerGame game, Player player) {
		objective = new Objective(game.getPlugin(), player, "Side bar", "Hunger Game");
		borderMoveObserver = new BorderMoveTimeLineObserver(game, objective);

		IBorder ov = game.getBorders().getBorder(WorldManager.OVERWORLD).get();
		add(score -> new BorderLocationEntry(score).addUpdater(UpdatersFactory.playerMove().condition(e -> e.getPlayer().equals(objective.getPlayer()))));
		add(score -> new BorderCenterEntry(score).addUpdater(UpdatersFactory.playerMove().condition(e -> e.getPlayer().equals(objective.getPlayer()))));
		objective.emptyEntry(-objective.entries().size());
		for (IBorder border : game.getBorders().toList())
			add(score -> new BorderSizeCountDownEntry(score, border, "#").setDisplayHalfSize(true).addUpdater(new TimeTaskObserverEntryUpdater()));

		Platform.get(game.getPlugin()).getTimeLine().register(ov.getStartTime().get(), borderMoveObserver);
	}

	/**
	 * @return The underlying objective constructed by this hunger game objective.
	 */
	public Objective getObjective() {
		return objective;
	}

	private void add(Function<Integer, IEntry> constructor) {
		objective.addEntry(constructor.apply(-objective.entries().size()));
	}
}
