package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.GameMode;

import fr.pederobien.minecraft.game.impl.time.CountDown;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.time.ICountDown;
import fr.pederobien.minecraft.game.interfaces.time.IObsTimeLine;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.entries.simple.TeamPlayerOnModeEntry;
import fr.pederobien.minecraft.scoreboards.impl.Objective;
import fr.pederobien.minecraft.scoreboards.impl.updaters.UpdatersFactory;
import fr.pederobien.minecraft.scoreboards.interfaces.IEntry;

public class BorderMoveTimeLineObserver implements IObsTimeLine {
	private Objective objective;
	private ICountDown countDown;

	public BorderMoveTimeLineObserver(IHungerGame game, Objective objective) {
		this.objective = objective;

		// Action when the count down is over
		Consumer<LocalTime> onTimeAction = time -> {
			objective.emptyEntry(-objective.entries().size());
			for (ITeam team : game.getTeams().toList())
				if (!team.getPlayers().toList().isEmpty())
					add(score -> new TeamPlayerOnModeEntry(score, team, GameMode.SURVIVAL, true).addUpdater(UpdatersFactory.playerGameModeChange()));
		};
		countDown = new CountDown(0, null, onTimeAction);
	}

	@Override
	public ICountDown getCountDown() {
		return countDown;
	}

	@Override
	public LocalTime getNextTime() {
		return null;
	}

	private void add(Function<Integer, IEntry> constructor) {
		objective.addEntry(constructor.apply(-objective.entries().size()));
	}
}