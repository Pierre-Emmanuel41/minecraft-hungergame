package fr.pederobien.minecrafthungergame.impl;

import java.time.LocalTime;

import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.impl.state.InGameState;
import fr.pederobien.minecrafthungergame.impl.state.InitialState;
import fr.pederobien.minecrafthungergame.impl.state.StartState;
import fr.pederobien.minecrafthungergame.impl.state.StopState;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IUnmodifiableHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.state.IGameState;
import fr.pederobien.minecraftmanagers.PlayerManager;

public class HungerGame implements IHungerGame {
	private IGameState initialState, startState, inGameState, stopState, current;
	private IUnmodifiableHungerGameConfiguration configuration;

	public HungerGame(IUnmodifiableHungerGameConfiguration configuration) {
		this.configuration = configuration;

		initialState = new InitialState(this);
		startState = new StartState(this);
		inGameState = new InGameState(this);
		stopState = new StopState(this);
		setCurrentState(initialState);
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
			Plateform.getNotificationCenter().sendMessage(new MinecraftMessageEvent(player, EHungerGameMessageCode.PVP_ENABLED));
		});
	}

	@Override
	public IGameState getCurrentState() {
		return current;
	}

	@Override
	public IGameState setCurrentState(IGameState current) {
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
	public IUnmodifiableHungerGameConfiguration getConfiguration() {
		return configuration;
	}
}
