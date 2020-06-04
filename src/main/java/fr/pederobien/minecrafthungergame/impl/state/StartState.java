package fr.pederobien.minecrafthungergame.impl.state;

import org.bukkit.GameMode;
import org.bukkit.GameRule;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.pederobien.minecraftgameplateform.helpers.TeamHelper;
import fr.pederobien.minecraftgameplateform.interfaces.element.IBorderConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecraftmanagers.PlayerManager;
import fr.pederobien.minecraftmanagers.PotionManager;
import fr.pederobien.minecraftmanagers.WorldManager;

public class StartState extends AbstractState {

	public StartState(IHungerGame game) {
		super(game);
	}

	@Override
	public void start() {
		giveEffects();
		updatePlayers();
		updateOverWorld();
		teleport();
		getGame().setCurrentState(getGame().getInGameState());
	}

	private void giveEffects() {
		PotionEffect resistance = PotionManager.createEffect(PotionEffectType.DAMAGE_RESISTANCE, 600, 1);
		PotionEffect regeneration = PotionManager.createEffect(PotionEffectType.REGENERATION, 600, 1);
		PotionEffect saturation = PotionManager.createEffect(PotionEffectType.SATURATION, 600, 1);
		PlayerManager.getPlayers().parallel().forEach(player -> PotionManager.giveEffects(player, resistance, regeneration, saturation));
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
		TeamHelper.createTeamsOnServer(getConfiguration().getTeams());
		IBorderConfiguration conf = getConfiguration().getBorders(WorldManager.OVERWORLD).get(0);
		getConfigurationHelper().teleportTeamsRandomly(WorldManager.OVERWORLD, conf.getBorderCenter(), conf.getInitialBorderDiameter());
	}
}
