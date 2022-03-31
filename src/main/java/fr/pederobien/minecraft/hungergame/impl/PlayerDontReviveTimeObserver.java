package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.dictionary.impl.MinecraftMessageEvent.MinecraftMessageEventBuilder;
import fr.pederobien.minecraft.dictionary.impl.PlayerGroup;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.dictionary.interfaces.IPlayerGroup;
import fr.pederobien.minecraft.game.event.GameStartPostEvent;
import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.game.impl.time.CountDown;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.game.interfaces.time.ICountDown;
import fr.pederobien.minecraft.game.interfaces.time.IObsTimeLine;
import fr.pederobien.minecraft.game.interfaces.time.ITimeLine;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.managers.EventListener;
import fr.pederobien.minecraft.managers.MessageManager.DisplayOption;
import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.minecraft.managers.PotionManager;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.minecraft.platform.event.ConfigurableValueChangeEvent;
import fr.pederobien.utils.IPausable.PausableState;
import fr.pederobien.utils.event.IEventListener;

public class PlayerDontReviveTimeObserver extends EventListener implements IObsTimeLine, ICodeSender, IEventListener, Listener {
	private IHungerGame game;
	private Consumer<Integer> countDownAction;
	private Consumer<LocalTime> onTimeAction;
	private ICountDown countDown;
	private boolean canRevive, canDestroy;
	private Map<Player, PlayerDeathInfo> deathInfo;

	/**
	 * Creates a time line observer in order to be notified when players cannot revive any more.
	 */
	public PlayerDontReviveTimeObserver(IHungerGame game) {
		this.game = game;

		// Action to perform during the count down
		countDownAction = count -> send(EHungerGameCode.HUNGER_GAME_NO_RESURRECTION_COUNT_DOWN, EColor.GOLD, DisplayOption.TITLE, count);

		// Action to perform when the count down is over.
		onTimeAction = time -> setCanRevive(false);

		deathInfo = new HashMap<Player, PlayerDeathInfo>();
		canRevive = true;

		register(game.getPlugin());
	}

	@Override
	public ICountDown getCountDown() {
		return countDown;
	}

	@Override
	public LocalTime getNextTime() {
		return null;
	}

	/**
	 * @return True if a player can revive, false otherwise.
	 */
	public boolean canRevive() {
		return canRevive;
	}

	@fr.pederobien.utils.event.EventHandler
	private void onGameStart(GameStartPostEvent event) {
		if (!event.getGame().equals(game))
			return;

		setActivated(true);
		ITimeLine timeLine = Platform.get(game.getPlugin()).getTimeLine();
		LocalTime playerDontReviveTime = game.getPlayerDontReviveTime().get();
		LocalTime realPlayerDontReviveTime = playerDontReviveTime.equals(LocalTime.MIN) ? LocalTime.of(0, 0, 1) : playerDontReviveTime;
		countDown = new CountDown(playerDontReviveTime.toSecondOfDay() < 5 ? playerDontReviveTime.toSecondOfDay() : 5, countDownAction, onTimeAction);
		timeLine.register(realPlayerDontReviveTime, this);
		canDestroy = false;
	}

	@fr.pederobien.utils.event.EventHandler
	private void onGameStop(GameStopPostEvent event) {
		if (!event.getGame().equals(game))
			return;

		setActivated(false);
		LocalTime playerDontReviveTime = game.getPlayerDontReviveTime().get();
		Platform.get(event.getGame().getPlugin()).getTimeLine().unregister(playerDontReviveTime, this);
		canRevive = true;
		canDestroy = true;
	}

	@fr.pederobien.utils.event.EventHandler
	private void onPlayerDontReviveTimeChange(ConfigurableValueChangeEvent event) {
		if (game.getState() == PausableState.NOT_STARTED || !event.getConfigurable().equals(game.getPlayerDontReviveTime()))
			return;

		ITimeLine timeLine = Platform.get(game.getPlugin()).getTimeLine();
		LocalTime oldPlayerDontReviveTime = (LocalTime) event.getOldValue();
		LocalTime newPlayerDontReviveTime = (LocalTime) event.getConfigurable().get();

		// Unregistering the observer for the old PVP time value
		timeLine.unregister(oldPlayerDontReviveTime, this);
		if (oldPlayerDontReviveTime.compareTo(newPlayerDontReviveTime) <= 0) {
			setCanRevive(true);
			deathInfo.values().forEach(info -> info.restore(newPlayerDontReviveTime));
		}

		int playerDontReviveTimeSecond = newPlayerDontReviveTime.toSecondOfDay();
		int gameTimeSecond = timeLine.getTimeTask().getGameTime().toSecondOfDay();

		int difference = playerDontReviveTimeSecond - gameTimeSecond;

		// Enabling the rule after the PVP time
		if (difference < 0)
			setCanRevive(false);
		else {
			// Enabling the rule during/before the count down
			countDown = new CountDown(difference < 5 ? difference : 5, countDownAction, onTimeAction);
			timeLine.register(newPlayerDontReviveTime, this);
		}
	}

	@org.bukkit.event.EventHandler
	private void onPlayerDie(PlayerDeathEvent event) {
		if (!isActivated())
			return;

		if (!canRevive) {
			if (event.getEntity().getKiller() instanceof Player) {
				Player killer = event.getEntity().getKiller();
				if (killer == null)
					return;

				if (killer.getInventory().firstEmpty() == -1)
					killer.getWorld().dropItem(killer.getLocation(), game.getItemOnPlayerKills().get());
				else
					killer.getInventory().addItem(game.getItemOnPlayerKills().get());
			}

			IndestructibleChest indestructibleChest = new IndestructibleChest(game, event.getEntity().getWorld(), event.getEntity().getLocation(), event.getDrops());
			indestructibleChest.pop();
			LocalTime gameTime = Platform.get(game.getPlugin()).getTimeLine().getTimeTask().getGameTime();
			deathInfo.put(event.getEntity(), new PlayerDeathInfo(event.getEntity(), gameTime, event.getEntity().getLocation()));
			PlayerManager.setGameModeOfPlayer(event.getEntity(), GameMode.SPECTATOR);
			event.getEntity().getInventory().clear();
		} else {
			PlayerManager.setGameModeOfPlayer(event.getEntity(), GameMode.SURVIVAL);
		}
		event.setKeepInventory(true);
	}

	private void setCanRevive(boolean canRevive) {
		if (this.canRevive == canRevive)
			return;

		this.canRevive = canRevive;
		send(canRevive ? EHungerGameCode.HUNGER_GAME__RESURRECT : EHungerGameCode.HUNGER_GAME__NO_RESURRECTION, EColor.DARK_RED, DisplayOption.CONSOLE);
	}

	private void send(IMinecraftCode code, EColor color, DisplayOption displayOption, Object... args) {
		MinecraftMessageEventBuilder builder = eventBuilder(code).withDisplayOption(displayOption).withColor(color);

		IPlayerGroup group = PlayerGroup.of("Players", player -> {
			for (ITeam team : game.getTeams())
				if (team.getPlayers().getPlayer(player.getName()).isPresent())
					return true;
			return false;
		});

		send(builder.withGroup(group).build(args));
	}

	private class IndestructibleChest extends EventListener {
		private World world;
		private Location location;
		private List<ItemStack> blocks;
		private Block left, right;

		public IndestructibleChest(IHungerGame game, World world, Location location, List<ItemStack> block) {
			this.world = world;
			this.location = location;
			this.blocks = block;

			register(game.getPlugin());
			setActivated(true);

			left = location.add(new Vector(0, 1, 0)).getBlock();
			right = location.add(new Vector(1, 0, 0)).getBlock();
		}

		/**
		 * Creates and populate the created chests with the stuff of the dead player.
		 */
		public void pop() {
			left.setType(Material.CHEST);
			right.setType(Material.CHEST);
			Chest leftChest = (Chest) left.getState(), rightChest = (Chest) right.getState();
			leftChest.getInventory().clear();
			rightChest.getInventory().clear();
			for (ItemStack leftStack : blocks) {
				Map<Integer, ItemStack> leftCannot = leftChest.getInventory().addItem(leftStack);
				for (ItemStack rightStack : leftCannot.values()) {
					Map<Integer, ItemStack> rightCannot = rightChest.getInventory().addItem(rightStack);
					for (ItemStack drop : rightCannot.values())
						world.dropItemNaturally(location, drop);
				}
			}
		}

		@org.bukkit.event.EventHandler
		private void onBlockBreakEvent(BlockBreakEvent event) {
			if (!isActivated() || canDestroy || !event.getBlock().equals(left) && !event.getBlock().equals(right))
				return;

			event.setCancelled(true);
		}

		@org.bukkit.event.EventHandler
		private void onBlockExplodes(EntityExplodeEvent event) {
			if (!isActivated() || canDestroy || !event.blockList().contains(left) && !event.blockList().contains(right))
				return;

			event.blockList().remove(left);
			event.blockList().remove(right);
		}
	}

	private class PlayerDeathInfo {
		private Player player;
		private LocalTime deathTime;
		private Location location;

		public PlayerDeathInfo(Player player, LocalTime deathTime, Location location) {
			this.player = player;
			this.deathTime = deathTime;
			this.location = location;
		}

		public void restore(LocalTime playerDontReviveTime) {
			if (playerDontReviveTime.compareTo(deathTime) < 0)
				return;

			PlayerManager.teleporte(player, location.add(new Vector(0, 2, 0)));
			PotionEffect fireResistance = PotionManager.createEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1);
			PotionEffect damageResistance = PotionManager.createEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 1);
			PotionEffect regeneration = PotionManager.createEffect(PotionEffectType.REGENERATION, 200, 1);
			PotionEffect saturation = PotionManager.createEffect(PotionEffectType.SATURATION, 200, 1);
			PotionManager.giveEffects(player, damageResistance, fireResistance, regeneration, saturation);
			PlayerManager.setGameModeOfPlayer(player, GameMode.SURVIVAL);
		}
	}
}