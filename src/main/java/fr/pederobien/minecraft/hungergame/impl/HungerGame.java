package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.HGPlugin;
import fr.pederobien.minecraft.hungergame.impl.state.InGameState;
import fr.pederobien.minecraft.hungergame.impl.state.InitialState;
import fr.pederobien.minecraft.hungergame.impl.state.StartState;
import fr.pederobien.minecraft.hungergame.impl.state.StopState;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameObjective;
import fr.pederobien.minecraft.hungergame.interfaces.state.IGameState;
import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.minecraftmanagers.MessageManager.DisplayOption;
import fr.pederobien.minecraftmanagers.ScoreboardManager;

public class HungerGame implements IHungerGame {
	private IGameState initialState, startState, inGameState, stopState, current;
	private IHungerGameConfiguration configuration;

	public HungerGame(IHungerGameConfiguration configuration) {
		this.configuration = configuration;

		initialState = new InitialState(this);
		startState = new StartState(this);
		inGameState = new InGameState(this);
		stopState = new StopState(this);
		current = initialState;
	}

	@Override
	public boolean initiate(CommandSender sender, Command command, String label, String[] args) {
		return current.initiate(sender, command, label, args);
	}

	@Override
	public void start() {
		for (ITeam team : getConfiguration().getTeams())
			for (Player player : team.getPlayers()) {
				IHungerGameObjective objective = new HungerGameObjective(HGPlugin.get(), player, "Side bar", "Hunger Game", getConfiguration());
				objective.setScoreboard(ScoreboardManager.createScoreboard());
				Plateform.getObjectiveUpdater().register(objective);
			}
		current.start();
	}

	@Override
	public void stop() {
		current.stop();
	}

	@Override
	public void pause() {
		current.pause();
	}

	@Override
	public void relaunch() {
		current.relaunch();
	}

	@Override
	public IEventListener getListener() {
		return current.getListener();
	}

	@Override
	public void onPvpEnabled() {
		sendNotSynchro(EHungerGameMessageCode.HG_PVP_ENABLED, DisplayOption.TITLE, EColor.DARK_RED);
	}

	@Override
	public boolean isRunning() {
		return current == inGameState;
	}

	@Override
	public IGameState getCurrentState() {
		return current;
	}

	@Override
	public IGameState setCurrentState(IGameState current) {
		this.current.getListener().setActivated(false);
		current.getListener().register(HGPlugin.get());
		current.getListener().setActivated(true);
		return this.current = current;
	}

	@Override
	public IGameState getInitialState() {
		return initialState;
	}

	@Override
	public IGameState getStartState() {
		return startState;
	}

	@Override
	public IGameState getInGameState() {
		return inGameState;
	}

	@Override
	public IGameState getStopState() {
		return stopState;
	}

	@Override
	public int getCountDown() {
		return current.getCountDown();
	}

	@Override
	public int getCurrentCountDown() {
		return current.getCurrentCountDown();
	}

	@Override
	public void onTime(LocalTime time) {
		current.onTime(time);
	}

	@Override
	public void onCountDownTime(LocalTime currentTime) {
		current.onCountDownTime(currentTime);
	}

	@Override
	public LocalTime getNextNotifiedTime() {
		return current.getNextNotifiedTime();
	}

	@Override
	public IHungerGameConfiguration getConfiguration() {
		return configuration;
	}
}
