package fr.pederobien.minecraft.hungergame.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.event.GamePausePostEvent;
import fr.pederobien.minecraft.game.event.GameResumePostEvent;
import fr.pederobien.minecraft.managers.EventListener;
import fr.pederobien.minecraft.managers.MessageManager;
import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.minecraft.managers.WorldManager;
import fr.pederobien.utils.IPausable.PausableState;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.IEventListener;

public class HungerGameEventListener extends EventListener implements IEventListener, ICodeSender {
	private HungerGame game;
	private List<Player> alivePlayers;

	/**
	 * Creates an event listener in order to handle events.
	 * 
	 * @param game The game associated to this event listener.
	 */
	public HungerGameEventListener(HungerGame game) {
		this.game = game;

		alivePlayers = new ArrayList<Player>();
		EventManager.registerListener(this);
	}

	@org.bukkit.event.EventHandler
	private void onPlayerMoveEvent(PlayerMoveEvent event) {
		if (!isActivated())
			return;

		if (game.getState() == PausableState.PAUSED && alivePlayers.contains(event.getPlayer())) {
			event.setCancelled(true);
			MessageManager.sendMessage(event.getPlayer(), ChatColor.RED + "Game paused, you can't move");
		}
	}

	@org.bukkit.event.EventHandler
	private void onPlayerRespawn(PlayerRespawnEvent event) {
		if (!isActivated() || event.getPlayer().getKiller() instanceof Player)
			return;

		Optional<IBorder> optBorder = game.getBorders().getBorder(WorldManager.OVERWORLD);
		if (optBorder.isPresent()) {
			IBorder border = optBorder.get();
			event.setRespawnLocation(WorldManager.getRandomlyLocationInOverworld(border.getCenter().get(), (int) border.getWorldBorder().getSize()));
		}
	}

	@org.bukkit.event.EventHandler
	private void onPlayerDie(PlayerDeathEvent event) {
		if (!isActivated())
			return;

		if (!game.getPlayerDontReviveTimeObserver().canRevive() || event.getEntity().getKiller() instanceof Player) {
			event.setKeepInventory(false);
			PlayerManager.setGameModeOfPlayer(event.getEntity(), GameMode.SPECTATOR);
			Player killer = event.getEntity().getKiller();
			if (killer == null)
				return;

			if (killer.getInventory().firstEmpty() == -1)
				killer.getWorld().dropItem(killer.getLocation(), game.getItemOnPlayerKills().get());
			else
				killer.getInventory().addItem(game.getItemOnPlayerKills().get());
		} else {
			event.setKeepInventory(true);
			PlayerManager.setGameModeOfPlayer(event.getEntity(), GameMode.SURVIVAL);
		}
	}

	@fr.pederobien.utils.event.EventHandler
	private void onGamePause(GamePausePostEvent event) {
		if (!event.getGame().equals(game))
			return;

		game.getTeams().stream().forEach(team -> {
			team.getPlayers().stream().filter(player -> player.getGameMode() == GameMode.SURVIVAL).forEach(player -> {
				alivePlayers.add(player);
				player.setGameMode(GameMode.SPECTATOR);
			});
		});
	}

	@fr.pederobien.utils.event.EventHandler
	private void onGameResume(GameResumePostEvent event) {
		if (!event.getGame().equals(game))
			return;

		alivePlayers.forEach(player -> player.setGameMode(GameMode.SURVIVAL));
		alivePlayers.clear();
	}
}
