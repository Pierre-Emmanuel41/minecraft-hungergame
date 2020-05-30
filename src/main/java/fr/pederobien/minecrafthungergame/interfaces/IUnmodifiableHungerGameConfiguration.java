package fr.pederobien.minecrafthungergame.interfaces;

import java.time.LocalTime;

import fr.pederobien.minecraftgameplateform.interfaces.element.unmodifiable.IUnmodifiableGameBorderConfiguration;

public interface IUnmodifiableHungerGameConfiguration extends IUnmodifiableGameBorderConfiguration {

	/**
	 * While the game time is less than this time, if a player dies, it will respawn in game mode survival and keep its inventory.
	 * Once this time is exceed, a player will respawn in spectator mode and drop its inventory if it dies.
	 * 
	 * @return The time after which a player respawn in spectator mode.
	 */
	LocalTime getPlayerDontReviveTime();
}
