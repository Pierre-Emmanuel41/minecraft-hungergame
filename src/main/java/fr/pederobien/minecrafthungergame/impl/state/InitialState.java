package fr.pederobien.minecrafthungergame.impl.state;

import fr.pederobien.minecraftgameplateform.interfaces.element.IBorderConfiguration;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecraftmanagers.PlayerManager;
import fr.pederobien.minecraftmanagers.ScoreboardManager;

public class InitialState extends AbstractState {

	public InitialState(IHungerGame game) {
		super(game);
	}

	@Override
	public boolean initiate() {
		for (IBorderConfiguration configuration : getConfiguration().getBorders()) {
			Plateform.getTimeLine().addObserver(configuration.getInitialTime(), configuration);
			Plateform.getTimeLine().addObserver(configuration.getStartTime(), configuration);
		}

		Plateform.getTimeLine().addObserver(getConfiguration().getPlayerDontReviveTime(), getGame());
		PlayerManager.getPlayers().parallel().forEach(player -> getGame().createObjective(ScoreboardManager.createScoreboard(), player));
		return true;
	}

	@Override
	public void start() {
		getGame().setCurrentState(getGame().getStartState()).start();
	}
}
