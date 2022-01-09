package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;

import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.pederobien.minecraft.border.impl.Border;
import fr.pederobien.minecraft.border.impl.BorderList;
import fr.pederobien.minecraft.border.impl.BorderTimeLineObserver;
import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.border.interfaces.IBorderList;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.impl.TeamHelper;
import fr.pederobien.minecraft.game.impl.TeamsFeaturesGame;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.hungergame.HGPlugin;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.minecraft.managers.PotionManager;
import fr.pederobien.minecraft.managers.WorldManager;
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
	private IConfigurable<LocalTime> pvpTime, playerDontReviveTime;
	private IConfigurable<Boolean> uhc;
	private IConfigurable<ItemStack> itemOnPlayerKills;
	private HungerGameStartTabExecutor startTabExecutor;
	private HungerGameStopTabExecutor stopTabExecutor;
	private HungerGameEventListener eventListener;
	private PlayerDontReviveTimeObserver playerDontReviveTimeObserver;

	/**
	 * Creates an hunger game to configure.
	 * 
	 * @param name The name of the game.
	 */
	public HungerGame(String name) {
		super(name, HGPlugin.instance());

		borders = new BorderList(name);
		rules = new RuleList(this);
		pvpTime = new Configurable<LocalTime>("pvpTime", LocalTime.of(0, 20, 0));
		uhc = new Configurable<Boolean>("uhc", false);
		itemOnPlayerKills = new Configurable<ItemStack>("itemOnPlayerKills", new ItemStack(Material.GOLDEN_APPLE));
		playerDontReviveTime = new Configurable<LocalTime>("playerDontReviveTime", LocalTime.of(0, 0, 0));
		startTabExecutor = new HungerGameStartTabExecutor(this);
		stopTabExecutor = new HungerGameStopTabExecutor();
		eventListener = new HungerGameEventListener(this);
		playerDontReviveTimeObserver = new PlayerDontReviveTimeObserver();

		getBorders().add(new Border("HungerGameDefaultBorder"));
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

	/**
	 * @return The time line observer that can specify if a player can revive or not.
	 */
	public PlayerDontReviveTimeObserver getPlayerDontReviveTimeObserver() {
		return playerDontReviveTimeObserver;
	}

	@EventHandler
	private void onGameStart(GameStartPostEvent event) {
		if (!event.getGame().equals(this))
			return;

		// Step 1: Registering time line observer in order to move border at the start time.
		for (IBorder border : getBorders().toList()) {
			border.getWorldBorder().setCenter(border.getCenter().get().getLocation());
			border.getWorldBorder().setSize(border.getInitialDiameter().get());
			new BorderTimeLineObserver(border, 5);
		}

		// Step 2: Modifying player properties
		giveEffects();
		updatePlayers();
		updateOverWorld();
		teleport();

		// Step 3: Activating event listener
		Platform.get(getPlugin()).getTimeLine().register(getPlayerDontReviveTime().get(), playerDontReviveTimeObserver);
		eventListener.register(getPlugin());
		eventListener.setActivated(true);

		// Step 4: Updating world game rule
		if (getUhc().get())
			getRules().getNaturalRegenerationGameRule().setValue(false);
	}

	@EventHandler
	private void onGameStop(GameStopPostEvent event) {
		if (!event.getGame().equals(this))
			return;

		// Step 1: Reseting borders and removing teams from the server.
		getBorders().toList().forEach(border -> border.getWorldBorder().reset());
		getTeams().forEach(team -> {
			TeamHelper.removeTeamFromServer(team);
			team.getPlayers().forEach(player -> player.setGameMode(GameMode.CREATIVE));
		});

		// Step 2: Unregistering time line observers and stopping objective updater.
		Platform platform = Platform.get(getPlugin());
		platform.getTimeLine().unregister(getPlayerDontReviveTime().get(), playerDontReviveTimeObserver);
		platform.getObjectiveUpdater().stop(true);
		eventListener.setActivated(false);
	}

	private void giveEffects() {
		PotionEffect resistance = PotionManager.createEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1);
		PotionEffect regeneration = PotionManager.createEffect(PotionEffectType.REGENERATION, 600, 1);
		PotionEffect saturation = PotionManager.createEffect(PotionEffectType.SATURATION, 600, 1);
		PlayerManager.getPlayers().forEach(player -> PotionManager.giveEffects(player, resistance, regeneration, saturation));
	}

	private void updatePlayers() {
		PlayerManager.maxFoodForPlayers();
		PlayerManager.resetMaxHealthOfPlayers();
		PlayerManager.maxLifeToPlayers();
		PlayerManager.removeInventoryOfPlayers();
		PlayerManager.resetLevelOfPlayers();
		PlayerManager.setGameModeOfAllPlayers(GameMode.SURVIVAL);
	}

	private void updateOverWorld() {
		WorldManager.setTime(WorldManager.OVERWORLD, 0);
		WorldManager.setStorm(WorldManager.OVERWORLD, false);
		WorldManager.setThundering(WorldManager.OVERWORLD, false);
		WorldManager.setGameRule(WorldManager.OVERWORLD, GameRule.DO_DAYLIGHT_CYCLE, true);
	}

	private void teleport() {
		TeamHelper.createTeamsOnServer(getTeams().toList());
		IBorder border = getBorders().getBorder(WorldManager.OVERWORLD).get();
		for (ITeam team : getTeams())
			TeamHelper.teleportTeamRandomly(team, border.getWorld().get(), border.getCenter().get(), border.getInitialDiameter().get());
	}
}
