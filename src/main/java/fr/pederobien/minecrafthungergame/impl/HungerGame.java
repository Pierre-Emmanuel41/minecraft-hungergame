package fr.pederobien.minecrafthungergame.impl;

import java.time.LocalTime;

import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IUnmodifiableHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.state.IGameState;

public class HungerGame implements IHungerGame {

	@Override
	public boolean initiate() {
		return false;
	}

	@Override
	public void start() {

	}

	@Override
	public void stop() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void relaunch() {

	}

	@Override
	public <T extends IEventListener> T getListener() {
		return null;
	}

	@Override
	public void onPvpEnabled() {

	}

	@Override
	public IGameState getCurrentState() {
		return null;
	}

	@Override
	public IGameState setCurrentState(IGameState current) {
		return null;
	}

	@Override
	public IGameState getInitialState() {
		return null;
	}

	@Override
	public IGameState getStartState() {
		return null;
	}

	@Override
	public IGameState getInGameState() {
		return null;
	}

	@Override
	public IGameState getStopState() {
		return null;
	}

	@Override
	public void timeChanged(LocalTime time) {

	}

	@Override
	public IUnmodifiableHungerGameConfiguration getConfiguration() {
		return null;
	}
}
