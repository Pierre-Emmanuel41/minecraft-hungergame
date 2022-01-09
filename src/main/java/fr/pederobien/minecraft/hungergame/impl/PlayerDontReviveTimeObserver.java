package fr.pederobien.minecraft.hungergame.impl;

import java.time.LocalTime;
import java.util.function.Consumer;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.impl.time.CountDown;
import fr.pederobien.minecraft.game.interfaces.time.ICountDown;
import fr.pederobien.minecraft.game.interfaces.time.IObsTimeLine;
import fr.pederobien.minecraft.managers.EColor;

public class PlayerDontReviveTimeObserver implements IObsTimeLine, ICodeSender {
	private ICountDown countDown;
	private boolean canRevive;

	/**
	 * Creates a time line observer in order to be notified when players cannot revive any more.
	 */
	public PlayerDontReviveTimeObserver() {
		canRevive = true;

		// Action to perform during the count down
		Consumer<Integer> countDownAction = count -> {
			send(eventBuilder(EHungerGameCode.HUNGER_GAME_NO_RESURRECTION_COUNT_DOWN).withColor(EColor.GOLD).build(count));
		};

		// Action to perform when the count down is over.
		Consumer<LocalTime> onTimeAction = time -> {
			send(eventBuilder(EHungerGameCode.HUNGER_GAME__NO_RESURRECTION).withColor(EColor.DARK_RED).build());
			canRevive = false;
		};

		countDown = new CountDown(5, countDownAction, onTimeAction);
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

	/**
	 * Players can now revive during a game.
	 */
	public void reset() {
		canRevive = true;
	}
}