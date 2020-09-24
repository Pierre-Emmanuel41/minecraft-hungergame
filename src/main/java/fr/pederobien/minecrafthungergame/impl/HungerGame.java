package fr.pederobien.minecrafthungergame.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.HGPlugin;
import fr.pederobien.minecrafthungergame.impl.state.InGameState;
import fr.pederobien.minecrafthungergame.impl.state.InitialState;
import fr.pederobien.minecrafthungergame.impl.state.StartState;
import fr.pederobien.minecrafthungergame.impl.state.StopState;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameObjective;
import fr.pederobien.minecrafthungergame.interfaces.state.IGameState;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.minecraftmanagers.MessageManager.DisplayOption;

public class HungerGame implements IHungerGame {
	private IGameState initialState, startState, inGameState, stopState, current;
	private IHungerGameConfiguration configuration;

	private List<IHungerGameObjective> objectives;

	public HungerGame(IHungerGameConfiguration configuration) {
		this.configuration = configuration;

		initialState = new InitialState(this);
		startState = new StartState(this);
		inGameState = new InGameState(this);
		stopState = new StopState(this);
		current = initialState;

		objectives = new ArrayList<IHungerGameObjective>();
	}

	@Override
	public boolean initiate(CommandSender sender, Command command, String label, String[] args) {
		return current.initiate(sender, command, label, args);
	}

	@Override
	public void start() {
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
		sendNotSynchro(EHungerGameMessageCode.PVP_ENABLED, DisplayOption.TITLE, EColor.DARK_RED);
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

	@Override
	public void createObjective(Scoreboard scoreboard, Player player) {
		IHungerGameObjective objective = new HungerGameObjective(HGPlugin.get(), player, "Side bar", "Hunger Game", getConfiguration());
		objective.setScoreboard(scoreboard);
		objectives.add(objective);
		Plateform.getObjectiveUpdater().register(objective);
	}

	@Override
	public List<IHungerGameObjective> getObjectives() {
		return Collections.unmodifiableList(objectives);
	}
}
