package fr.pederobien.minecrafthungergame.impl.state;

import java.time.LocalTime;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftgameplateform.exceptions.StateException;
import fr.pederobien.minecraftgameplateform.impl.element.EventListener;
import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.HGPlugin;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.state.IGameState;

public abstract class AbstractState implements IGameState {
	private IHungerGame game;
	private IGameConfigurationHelper helper;

	protected AbstractState(IHungerGame game) {
		this.game = game;
		helper = Plateform.getOrCreateConfigurationHelper(getConfiguration());
	}

	@Override
	public void timeChanged(LocalTime time) {
		if (time.equals(getConfiguration().getPlayerDontReviveTime())) {
			// Permission of message PLAYER_DONT_REVIVE is ALL, we don't need to specify a player for the event.
			Plateform.getNotificationCenter().sendMessage(new MinecraftMessageEvent(null, EHungerGameMessageCode.PLAYER_DONT_REVIVE));
			onPlayerDontRevive();
		}
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
	protected IHungerGameConfiguration getConfiguration() {
		return getGame().getConfiguration();
	}

	/**
	 * @return A game configuration helper associated to the configuration returned by {@link #getConfiguration()}. The helper is
	 *         defined in the constructor of this state. This means that if the configuration changed, the helper could have bad
	 *         result.
	 */
	protected IGameConfigurationHelper getConfigurationHelper() {
		return helper;
	}

	/**
	 * Method called when the time returned by {@link IHungerGameConfiguration#getPlayerDontReviveTime()} is exceeded. Do nothing if
	 * not overrided.
	 */
	protected void onPlayerDontRevive() {
	}

	/**
	 * This is a convenient method and is equivalent to <code>Plateform.getPluginManager().getPlugin(HGPlugin.NAME).get()</code>.
	 * 
	 * @return This plugin registered in the plateform.
	 */
	protected Plugin getPlugin() {
		return Plateform.getPluginHelper().getPlugin(HGPlugin.NAME).get();
	}
}
