package fr.pederobien.minecraft.hungergame.interfaces;

import java.time.LocalTime;

import org.bukkit.inventory.ItemStack;

import fr.pederobien.minecraft.border.interfaces.IBorderConfigurable;
import fr.pederobien.minecraft.game.interfaces.IFeatureConfigurable;
import fr.pederobien.minecraft.game.interfaces.IGame;
import fr.pederobien.minecraft.game.interfaces.ITeamConfigurable;
import fr.pederobien.minecraft.hungergame.impl.HungerGameStartTabExecutor;
import fr.pederobien.minecraft.hungergame.impl.HungerGameStopTabExecutor;
import fr.pederobien.minecraft.platform.interfaces.IConfigurable;
import fr.pederobien.minecraft.platform.interfaces.INominable;
import fr.pederobien.minecraft.platform.interfaces.IPvpTimeConfigurable;
import fr.pederobien.minecraft.rules.interfaces.IRuleConfigurable;

public interface IHungerGame extends IGame, INominable, ITeamConfigurable, IFeatureConfigurable, IBorderConfigurable, IPvpTimeConfigurable, IRuleConfigurable {

	/**
	 * {@inheritDoc}
	 */
	HungerGameStartTabExecutor getStartTabExecutor();

	/**
	 * {@inheritDoc}
	 */
	HungerGameStopTabExecutor getStopTabExecutor();

	/**
	 * @return A configurable object for the UHC mode.
	 */
	IConfigurable<Boolean> getUhc();

	/**
	 * @return A configurable object for the item stack given to a player when it kills another player.
	 */
	IConfigurable<ItemStack> getItemOnPlayerKills();

	/**
	 * @return A configurable object for the time after which player don't revive.
	 */
	IConfigurable<LocalTime> getPlayerDontReviveTime();
}
