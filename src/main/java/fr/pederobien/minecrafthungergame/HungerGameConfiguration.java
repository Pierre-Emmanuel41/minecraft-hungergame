package fr.pederobien.minecrafthungergame;

import java.time.LocalTime;
import java.util.StringJoiner;

import fr.pederobien.minecraftdevelopmenttoolkit.utils.DisplayHelper;
import fr.pederobien.minecraftgameplateform.border.BorderConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.AbstractGameBorderConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.PlateformTeam;
import fr.pederobien.minecraftgameplateform.interfaces.element.IGame;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.EColor;
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
		add(PlateformTeam.of("knights", EColor.DARK_AQUA));
		add(PlateformTeam.of("vikings", EColor.GREEN));
		add(PlateformTeam.of("barbarics", EColor.DARK_RED));
		add(PlateformTeam.of("spartiates", EColor.GOLD));
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
		return joiner.toString();
	}
}
