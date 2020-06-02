package fr.pederobien.minecrafthungergame.impl.state;

import org.bukkit.GameMode;

import fr.pederobien.minecraftgameplateform.helpers.TeamHelper;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecraftmanagers.PlayerManager;
import fr.pederobien.minecraftmanagers.WorldManager;

public class StopState extends AbstractState {

	public StopState(IHungerGame game) {
		super(game);
	}

	@Override
	public void stop() {
		WorldManager.OVERWORLD.getWorldBorder().reset();
		WorldManager.NETHER_WORLD.getWorldBorder().reset();
		WorldManager.END_WORLD.getWorldBorder().reset();
		TeamHelper.removeTeamsFromServer(getConfiguration().getTeams());
		PlayerManager.setGameModeOfAllPlayers(GameMode.CREATIVE);
		Plateform.getTimeTask().stop();
		getGame().setCurrentState(getGame().getInitialState());
	}
}
