package fr.pederobien.minecrafthungergame.impl.state;

import java.time.LocalTime;

import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftgameplateform.exceptions.StateException;
import fr.pederobien.minecraftgameplateform.impl.element.EventListener;
import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IUnmodifiableHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.state.IGameState;
import fr.pederobien.minecraftmanagers.PlayerManager;

public abstract class AbstractState implements IGameState {
	private IHungerGame game;

	protected AbstractState(IHungerGame game) {
		this.game = game;
	}

	@Override
	public void timeChanged(LocalTime time) {
		if (time.equals(getConfiguration().getPlayerDontReviveTime()))
			PlayerManager.getPlayers().parallel().forEach(player -> {
				Plateform.getNotificationCenter().sendMessage(new MinecraftMessageEvent(player, EHungerGameMessageCode.PLAYER_DONT_REVIVE));
			});
	}

	@Override
	public boolean initiate() {
		throw new StateException(this);
	}

	@Override
	public void start() {
		throw new StateException(this);
	}

	@Override
	public void pause() {
		throw new StateException(this);
	}

	@Override
	public void relaunch() {
		throw new StateException(this);
	}

	@Override
	public void stop() {
		throw new StateException(this);
	}

	@Override
	public IEventListener getListener() {
		return new EventListener();
	}

	/**
	 * @return The game managed by this state.
	 */
	protected IHungerGame getGame() {
		return game;
	}

	/**
	 * @return The configuration associated to this game.
	 */
	protected IUnmodifiableHungerGameConfiguration getConfiguration() {
		return getGame().getConfiguration();
	}
}
