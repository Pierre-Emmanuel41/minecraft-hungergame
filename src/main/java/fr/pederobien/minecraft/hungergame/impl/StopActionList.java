package fr.pederobien.minecraft.hungergame.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.GameMode;

import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.interfaces.IStopActionList;
import fr.pederobien.minecraft.managers.PlayerManager;

public class StopActionList implements IStopActionList {
	private IHungerGame game;
	private Map<StopAction, Boolean> startActions;

	/**
	 * Creates a new action list. This list gather the action to perform before starting a game.
	 */
	public StopActionList(IHungerGame game) {
		this.game = game;
		startActions = new LinkedHashMap<StopAction, Boolean>();
		for (StopAction stopAction : StopAction.values())
			startActions.put(stopAction, true);
	}

	@Override
	public void set(StopAction stopAction, boolean value) {
		startActions.put(stopAction, value);
	}

	@Override
	public void reset() {
		for (StopAction stopAction : StopAction.values())
			set(stopAction, true);
	}

	@Override
	public void stop() {
		doIf(StopAction.RESET_BORDERS, () -> game.getBorders().toList().forEach(border -> border.getWorldBorder().reset()));
		game.getTeams().forEach(team -> {
			doIf(StopAction.DELETE_SERVER_TEAM, () -> team.removeFromServer());
			doIf(StopAction.GAME_MODE, () -> team.getPlayers().forEach(player -> PlayerManager.setGameModeOfPlayer(player, GameMode.CREATIVE)));
		});
	}

	private void doIf(StopAction stopAction, Runnable runnable) {
		if (startActions.get(stopAction))
			runnable.run();
	}
}
