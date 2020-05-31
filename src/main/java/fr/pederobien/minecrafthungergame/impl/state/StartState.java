package fr.pederobien.minecrafthungergame.impl.state;

import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;

public class StartState extends AbstractState {

	public StartState(IHungerGame game) {
		super(game);
	}

	@Override
	public void start() {
		getGame().setCurrentState(getGame().getInGameState());
	}
}
