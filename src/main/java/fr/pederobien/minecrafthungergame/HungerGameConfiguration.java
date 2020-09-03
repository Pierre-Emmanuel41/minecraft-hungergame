package fr.pederobien.minecrafthungergame;

import java.time.LocalTime;
import java.util.StringJoiner;

import fr.pederobien.minecraftdevelopmenttoolkit.utils.DisplayHelper;
import fr.pederobien.minecraftborder.impl.BorderConfiguration;
import fr.pederobien.minecraftborder.impl.AbstractGameBorderConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.element.IGame;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecrafthungergame.impl.HungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftmanagers.WorldManager;

public class HungerGameConfiguration extends AbstractGameBorderConfiguration implements IHungerGameConfiguration {
	private static final LocalTime DEFAULT_PLAYER_DONT_REVIVE_TIME = LocalTime.of(0, 0, 0);

	private IGame game;
	private LocalTime playerDontReviveTime;

	public HungerGameConfiguration(String name) {
		super(name);
		game = new HungerGame(this);

		add(new BorderConfiguration("DefaultHGOverworldBorder"));
	}

	@Override
	public IGame getGame() {
		return game;
	}

	@Override
	public LocalTime getPlayerDontReviveTime() {
		return playerDontReviveTime == null ? DEFAULT_PLAYER_DONT_REVIVE_TIME : playerDontReviveTime;
	}

	@Override
	public void setPlayerDontReviveTime(LocalTime playerDontReviveTime) {
		this.playerDontReviveTime = playerDontReviveTime;
	}

	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add("Name : " + getName());
		joiner.add("Teams :" + (getTeams().isEmpty() ? " none" : ""));
		for (ITeam team : getTeams())
			joiner.add(team.toString());

		joiner.add("Borders :" + (getBorders().isEmpty() ? "none" : ""));
		if (!getBorders().isEmpty()) {
			joiner.add(getWorldBorders(WorldManager.OVERWORLD));
			joiner.add(getWorldBorders(WorldManager.NETHER_WORLD));
			joiner.add(getWorldBorders(WorldManager.END_WORLD));
		}
		joiner.add("Player don't revive time : " + display(playerDontReviveTime, DisplayHelper.toString(getPlayerDontReviveTime(), true)));
		joiner.add("Pvp time : " + DisplayHelper.toString(getPvpTime(), true));
		return joiner.toString();
	}
}
