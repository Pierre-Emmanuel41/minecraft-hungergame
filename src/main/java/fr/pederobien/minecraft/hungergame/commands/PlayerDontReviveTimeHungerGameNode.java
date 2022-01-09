package fr.pederobien.minecraft.hungergame.commands;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.DisplayHelper;
import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.impl.EPlatformCode;

public class PlayerDontReviveTimeHungerGameNode extends HungerGameNode {

	/**
	 * Creates a node to set the time after which players can't revive.
	 * 
	 * @param game The game associated to this node.
	 */
	protected PlayerDontReviveTimeHungerGameNode(Supplier<IHungerGame> game) {
		super(game, "playerDontReviveTime", EHungerGameCode.HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__EXPLANATION, g -> g != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, EPlatformCode.TIME_FORMAT__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		LocalTime time;
		try {
			time = LocalTime.parse(args[0]);
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__TIME_IS_MISSING, getGame().getName()));
			return false;
		} catch (DateTimeParseException e) {
			send(eventBuilder(sender, EGameCode.BAD_FORMAT, getMessage(sender, EPlatformCode.TIME_FORMAT__COMPLETION)));
			return false;
		}

		getGame().getPlayerDontReviveTime().set(time);
		if (time.equals(LocalTime.MIN))
			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__FROM_THE_BEGINNING);
		else {
			String timeFormat = DisplayHelper.toString(getGame().getPlayerDontReviveTime().get(), false);
			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__PLAYER_DONT_REVIVE_TIME__TIME_UPDATED, timeFormat);
		}
		return true;
	}
}
