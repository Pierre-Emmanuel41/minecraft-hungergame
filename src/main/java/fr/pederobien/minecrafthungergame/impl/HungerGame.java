package fr.pederobien.minecrafthungergame.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
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
import fr.pederobien.minecraftmanagers.MessageManager;
import fr.pederobien.minecraftmanagers.PlayerManager;

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
	public boolean initiate() {
		return current.initiate();
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
		PlayerManager.getPlayers().parallel().forEach(player -> {
			MessageManager.sendMessage(player, Plateform.getNotificationCenter().getMessage(new MinecraftMessageEvent(player, EHungerGameMessageCode.PVP_ENABLED)));
		});
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
		current.getListener().register(Plateform.getPluginHelper().getPlugin(HGPlugin.NAME).get());
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
	public void timeChanged(LocalTime time) {
		current.timeChanged(time);
	}

	@Override
	public IHungerGameConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public void createObjective(Scoreboard scoreboard, Player player) {
		IHungerGameObjective objective = new HungerGameObjective(getPlugin(), player, "Side bar", "Hunger Game", getConfiguration());
		objective.setScoreboard(scoreboard);
		objectives.add(objective);
		Plateform.getObjectiveUpdater().register(objective);
	}

	@Override
	public List<IHungerGameObjective> getObjectives() {
		return Collections.unmodifiableList(objectives);
	}

	/**
	 * This is a convenient method and is equivalent to <code>Plateform.getPluginManager().getPlugin(HGPlugin.NAME).get()</code>.
	 * 
	 * @return This plugin registered in the plateform.
	 */
	private Plugin getPlugin() {
		return Plateform.getPluginHelper().getPlugin(HGPlugin.NAME).get();
	}
}
