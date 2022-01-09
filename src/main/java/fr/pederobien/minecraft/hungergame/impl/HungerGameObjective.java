package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;
import java.util.function.Consumer;
import java.util.function.Function;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.border.entries.BorderSizeCountDownEntry;
import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.game.impl.time.CountDown;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.time.ICountDown;
import fr.pederobien.minecraft.game.interfaces.time.IObsTimeLine;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.WorldManager;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.minecraft.platform.entries.simple.CenterEntry;
import fr.pederobien.minecraft.platform.entries.simple.LocationSynchronizedEntry;
import fr.pederobien.minecraft.platform.entries.simple.TeamPlayerOnModeEntry;
import fr.pederobien.minecraft.platform.entries.updaters.TimeTaskObserverEntryUpdater;
import fr.pederobien.minecraft.scoreboards.impl.Objective;
import fr.pederobien.minecraft.scoreboards.impl.updaters.UpdatersFactory;
import fr.pederobien.minecraft.scoreboards.interfaces.IEntry;

public class HungerGameObjective extends Objective {
	private IHungerGame game;
	private BorderMoveTimeLineObserver borderMoveObserver;

	public HungerGameObjective(IHungerGame game, Player player) {
		super(game.getPlugin(), player, "Side bar", "Hunger Game");
		this.game = game;

		borderMoveObserver = new BorderMoveTimeLineObserver(game);
	}

	@Override
	public void start() {
		IBorder ov = game.getBorders().getBorder(WorldManager.OVERWORLD).get();
		add(score -> new LocationSynchronizedEntry(score).addUpdater(UpdatersFactory.playerMove().condition(e -> e.getPlayer().equals(getPlayer()))));
		add(score -> new CenterEntry(score, ov.getCenter().get()).addUpdater(UpdatersFactory.playerMove().condition(e -> e.getPlayer().equals(getPlayer()))));
		emptyEntry(-entries().size());
		for (IBorder border : game.getBorders().toList())
			add(score -> new BorderSizeCountDownEntry(score, border, "#").setDisplayHalfSize(true).addUpdater(new TimeTaskObserverEntryUpdater()));
		Platform.get(getPlugin()).getTimeLine().register(ov.getStartTime().get(), borderMoveObserver);
		super.start();
	}

	@Override
	public void stop() {
		Platform.get(getPlugin()).getTimeLine().unregister(game.getBorders().getBorder(WorldManager.OVERWORLD).get().getStartTime().get(), borderMoveObserver);
		super.stop();
	}

	private void add(Function<Integer, IEntry> constructor) {
		addEntry(constructor.apply(-entries().size()));
	}

	public class BorderMoveTimeLineObserver implements IObsTimeLine {
		private ICountDown countDown;

		public BorderMoveTimeLineObserver(IHungerGame game) {
			// Action when the count down is over
			Consumer<LocalTime> onTimeAction = time -> {
				emptyEntry(-entries().size());
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
	}
}
