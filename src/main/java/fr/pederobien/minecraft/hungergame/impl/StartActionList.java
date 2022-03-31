package fr.pederobien.minecraft.hungergame.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.pederobien.minecraft.border.impl.BorderTimeLineObserver;
import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.game.impl.TeamHelper;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.interfaces.IStartActionList;
import fr.pederobien.minecraft.managers.PlayerManager;
import fr.pederobien.minecraft.managers.PotionManager;
import fr.pederobien.minecraft.managers.ScoreboardManager;
import fr.pederobien.minecraft.managers.WorldManager;
import fr.pederobien.minecraft.platform.Platform;
import fr.pederobien.minecraft.scoreboards.interfaces.IObjective;
import fr.pederobien.minecraft.scoreboards.interfaces.IObjectiveUpdater;

public class StartActionList implements IStartActionList {
	private IHungerGame game;
	private Map<StartAction, Boolean> startActions;

	/**
	 * Creates a new action list. This list gather the action to perform before starting a game.
	 */
	public StartActionList(IHungerGame game) {
		this.game = game;
		startActions = new LinkedHashMap<StartAction, Boolean>();
		for (StartAction startAction : StartAction.values())
			startActions.put(startAction, true);
	}

	@Override
	public void set(StartAction startAction, boolean value) {
		startActions.put(startAction, value);
	}

	@Override
	public void reset() {
		for (StartAction startAction : StartAction.values())
			set(startAction, true);
	}

	@Override
	public void start() {
		Platform platform = Platform.get(game.getPlugin());

		game.getBorders().toList().forEach(border -> {
			doIf(StartAction.INITIALIZE_BORDERS, () -> {
				border.getWorldBorder().setCenter(border.getCenter().get().getLocation());
				border.getWorldBorder().setSize(border.getInitialDiameter().get());
			});
			new BorderTimeLineObserver(border, game);
		});

		IBorder border = game.getBorders().getBorder(WorldManager.OVERWORLD).get();
		IObjectiveUpdater objectiveUpdater = platform.getObjectiveUpdater();

		game.getTeams().toList().forEach(team -> {
			team.getPlayers().toList().forEach(player -> {
				doIf(StartAction.GIVE_EFFECT, () -> {
					PotionEffect resistance = PotionManager.createEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1);
					PotionEffect regeneration = PotionManager.createEffect(PotionEffectType.REGENERATION, 600, 1);
					PotionEffect saturation = PotionManager.createEffect(PotionEffectType.SATURATION, 600, 1);
					PotionManager.giveEffects(player, resistance, regeneration, saturation);
				});
				doIf(StartAction.FOOD_LEVEL, () -> PlayerManager.setFoodLevelOfPlayer(player, 20));
				doIf(StartAction.MAX_HEALTH, () -> PlayerManager.setMaxHealthOfPlayer(player, 20.0));
				doIf(StartAction.HEALTH, () -> PlayerManager.setHealthOfPlayer(player, 20));
				doIf(StartAction.CLEAN_INVENTORY, () -> PlayerManager.removeInventoryOfPlayer(player));
				doIf(StartAction.EXP_LEVEL, () -> PlayerManager.setExpLevelOfPlayer(player, 0));
				doIf(StartAction.GAME_MODE, () -> PlayerManager.setGameModeOfPlayer(player, GameMode.SURVIVAL));

				IObjective objective = new HungerGameObjective(game, player).getObjective();
				objective.setScoreboard(ScoreboardManager.createScoreboard());
				objectiveUpdater.register(objective);
			});

			doIf(StartAction.CREATE_SERVER_TEAM, () -> team.createOnserver());
			doIf(StartAction.TELEPORT, () -> TeamHelper.teleportTeamRandomly(team, border.getWorld().get(), border.getCenter().get(), border.getInitialDiameter().get()));
		});

		doIf(StartAction.SET_DAY, () -> WorldManager.setTime(WorldManager.OVERWORLD, 0));
		doIf(StartAction.WEATHER_CLEAR, () -> {
			WorldManager.setStorm(WorldManager.OVERWORLD, false);
			WorldManager.setThundering(WorldManager.OVERWORLD, false);
		});
		doIf(StartAction.DO_DAY_LIGHT_CYCLE, () -> WorldManager.setGameRule(WorldManager.OVERWORLD, GameRule.DO_DAYLIGHT_CYCLE, true));
		doIf(StartAction.DO_FIRE_TICK, () -> WorldManager.setGameRule(WorldManager.OVERWORLD, GameRule.DO_FIRE_TICK, false));
		doIf(StartAction.NATURAL_REGENERATION, () -> {
			if (!game.getUhc().get())
				WorldManager.setGameRule(WorldManager.OVERWORLD, GameRule.NATURAL_REGENERATION, true);
		});
	}

	private void doIf(StartAction startAction, Runnable runnable) {
		if (startActions.get(startAction))
			runnable.run();
	}
}
