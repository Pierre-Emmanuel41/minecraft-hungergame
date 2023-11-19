package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.pederobien.minecraft.border.impl.Border;
import fr.pederobien.minecraft.border.impl.BorderList;
import fr.pederobien.minecraft.border.interfaces.IBorderList;
import fr.pederobien.minecraft.chat.impl.ChatFeature;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.impl.TeamsFeaturesGame;
import fr.pederobien.minecraft.hungergame.HGPlugin;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.interfaces.IStartActionList;
import fr.pederobien.minecraft.hungergame.interfaces.IStopActionList;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.minecraft.platform.impl.Configurable;
import fr.pederobien.minecraft.platform.interfaces.IConfigurable;
import fr.pederobien.minecraft.rules.impl.RuleList;
import fr.pederobien.minecraft.rules.interfaces.IRuleList;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class HungerGame extends TeamsFeaturesGame implements IHungerGame, ICodeSender, IEventListener {
	private IBorderList borders;
	private IRuleList rules;
	private IStartActionList startActionList;
	private IStopActionList stopActionList;
	private IConfigurable<LocalTime> pvpTime, playerDontReviveTime;
	private IConfigurable<Boolean> uhc;
	private IConfigurable<ItemStack> itemOnPlayerKills;
	private HungerGameStartTabExecutor startTabExecutor;
	private HungerGameStopTabExecutor stopTabExecutor;
	private HungerGameEventListener eventListener;

	/**
	 * Creates an hunger game to configure.
	 * 
	 * @param name The name of the game.
	 */
	public HungerGame(String name) {
		super(name, HGPlugin.instance());

		borders = new BorderList(this);
		rules = new RuleList(this);
		startActionList = new StartActionList(this);
		stopActionList = new StopActionList(this);
		pvpTime = new Configurable<LocalTime>("pvpTime", LocalTime.of(0, 20, 0));
		uhc = new Configurable<Boolean>("uhc", false);
		itemOnPlayerKills = new Configurable<ItemStack>("itemOnPlayerKills", new ItemStack(Material.GOLDEN_APPLE));
		playerDontReviveTime = new Configurable<LocalTime>("playerDontReviveTime", LocalTime.of(0, 0, 0));
		startTabExecutor = new HungerGameStartTabExecutor(this);
		stopTabExecutor = new HungerGameStopTabExecutor(this);
		eventListener = new HungerGameEventListener(this);

		getBorders().add(new Border("HungerGameDefaultBorder"));
		addFeaturesIfPluginPresent();
		EventManager.registerListener(this);
	}

	@Override
	public IBorderList getBorders() {
		return borders;
	}

	@Override
	public IConfigurable<LocalTime> getPvpTime() {
		return pvpTime;
	}

	@Override
	public IRuleList getRules() {
		return rules;
	}

	@Override
	public HungerGameStartTabExecutor getStartTabExecutor() {
		return startTabExecutor;
	}

	@Override
	public HungerGameStopTabExecutor getStopTabExecutor() {
		return stopTabExecutor;
	}

	@Override
	public IStartActionList getStartActionList() {
		return startActionList;
	}

	@Override
	public IStopActionList getStopActionList() {
		return stopActionList;
	}

	@Override
	public IConfigurable<Boolean> getUhc() {
		return uhc;
	}

	@Override
	public IConfigurable<ItemStack> getItemOnPlayerKills() {
		return itemOnPlayerKills;
	}

	@Override
	public IConfigurable<LocalTime> getPlayerDontReviveTime() {
		return playerDontReviveTime;
	}

	@Override
	public String toString() {
		return getName();
	}

	@EventHandler
	private void onGameStart(GameStartPostEvent event) {
		if (!event.getGame().equals(this))
			return;

		startActionList.start();
		eventListener.setActivated(true);

		if (getUhc().get())
			getRules().getNaturalRegenerationGameRule().setValue(false);
	}

	@EventHandler
	private void onGameStop(GameStopPostEvent event) {
		if (!event.getGame().equals(this))
			return;

		stopActionList.stop();

		Platform platform = Platform.get(getPlugin());
		platform.getObjectiveUpdater().stop(true);
		eventListener.setActivated(false);
	}

	private void addFeaturesIfPluginPresent() {
		try {
			getFeatures().add(new ChatFeature(this));
		} catch (NoClassDefFoundError e) {
			// do nothing
		}
	}
}
