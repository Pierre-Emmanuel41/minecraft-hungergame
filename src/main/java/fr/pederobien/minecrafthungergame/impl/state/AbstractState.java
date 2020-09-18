package fr.pederobien.minecrafthungergame.impl.state;

import java.time.LocalTime;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraftdictionary.impl.MinecraftMessageEvent;
import fr.pederobien.minecraftgameplateform.exceptions.StateException;
import fr.pederobien.minecraftgameplateform.impl.element.EventListener;
import fr.pederobien.minecraftgameplateform.interfaces.element.IEventListener;
import fr.pederobien.minecraftgameplateform.interfaces.helpers.IGameConfigurationHelper;
import fr.pederobien.minecraftgameplateform.utils.EColor;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.HGPlugin;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.state.IGameState;
import fr.pederobien.minecraftmanagers.MessageManager;
import fr.pederobien.minecraftmanagers.MessageManager.DisplayOption;
import fr.pederobien.minecraftmanagers.MessageManager.TitleMessage;
import fr.pederobien.minecraftmanagers.PlayerManager;

public abstract class AbstractState implements IGameState {
	private IHungerGame game;
	private IGameConfigurationHelper helper;
	private int currentCountDown;

	protected AbstractState(IHungerGame game) {
		this.game = game;
		helper = Plateform.getOrCreateConfigurationHelper(getConfiguration());
		currentCountDown = getCountDown();
	}

	@Override
	public int getCountDown() {
		return 5;
	}

	@Override
	public int getCurrentCountDown() {
		return currentCountDown;
	}

	@Override
	public void onTime(LocalTime time) {
		// Permission of message PLAYER_DONT_REVIVE is ALL, we don't need to specify a player for the event.
		PlayerManager.getPlayers().forEach(player -> {
			String message = Plateform.getNotificationCenter().getMessage(new MinecraftMessageEvent(player, EHungerGameMessageCode.PLAYER_DONT_REVIVE));
			MessageManager.sendMessage(DisplayOption.TITLE, player, TitleMessage.of(message, EColor.DARK_RED.getName()));
		});
		onPlayerDontRevive();
		currentCountDown = getCountDown();
	}

	@Override
	public void onCountDownTime(LocalTime currentTime) {
		PlayerManager.getPlayers().forEach(player -> {
			String message = Plateform.getNotificationCenter()
					.getMessage(new MinecraftMessageEvent(player, EHungerGameMessageCode.PLAYER_WILL_NOT_REVIVE_IN, currentCountDown));
			MessageManager.sendMessage(DisplayOption.TITLE, player, TitleMessage.of(message, EColor.GOLD.getName()));
		});
		currentCountDown--;
	}

	@Override
	public LocalTime getNextNotifiedTime() {
		return LocalTime.of(0, 0, 0);
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
