package fr.pederobien.minecraft.hungergame.impl.state;

import org.bukkit.GameMode;

import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraftgameplateform.helpers.TeamHelper;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecraftmanagers.PlayerManager;

public class StopState extends AbstractState {

	public StopState(IHungerGame game) {
		super(game);
	}

	@Override
	public void stop() {
		getConfiguration().getBorders().forEach(border -> border.reset());
		TeamHelper.removeTeamsFromServer(getConfiguration().getTeams());
		PlayerManager.setGameModeOfAllPlayers(GameMode.CREATIVE);
		Plateform.getObjectiveUpdater().stop(true);
		getGame().setCurrentState(getGame().getInitialState());
	}
}
