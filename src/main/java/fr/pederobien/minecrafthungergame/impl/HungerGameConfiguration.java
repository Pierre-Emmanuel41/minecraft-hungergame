package fr.pederobien.minecrafthungergame.impl;

import java.time.LocalTime;
import java.util.StringJoiner;

import fr.pederobien.minecraftdevelopmenttoolkit.utils.DisplayHelper;
import fr.pederobien.minecraftgameplateform.border.BorderConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.GameBorderConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.element.IGame;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftmanagers.WorldManager;

public class HungerGameConfiguration extends GameBorderConfiguration implements IHungerGameConfiguration {
	private static final LocalTime DEFAULT_PLAYER_DONT_REVIVE_TIME = LocalTime.of(0, 0, 0);

	private LocalTime playerDontReviveTime;

	protected HungerGameConfiguration(String name, IGame game) {
		super(name, game);
		add(new BorderConfiguration("DefaultHGOverworldBorder"));
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
		joiner.add("Teams :" + (getTeams().isEmpty() ? "" : " none"));
		for (ITeam team : getTeams())
			joiner.add(team.toString());

		if (getBorders().isEmpty())
			joiner.add("Borders :" + (getBorders().size() > 0 ? "" : " none"));
		else {
			joiner.add(getWorldBorders(WorldManager.OVERWORLD));
			joiner.add(getWorldBorders(WorldManager.NETHER_WORLD));
			joiner.add(getWorldBorders(WorldManager.END_WORLD));
		}
		joiner.add("Player don't revive time : " + display(playerDontReviveTime, DisplayHelper.toString(getPlayerDontReviveTime())));
		return super.toString();
	}
}
