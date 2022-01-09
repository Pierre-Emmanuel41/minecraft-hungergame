package fr.pederobien.minecraft.hungergame.commands;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.game.impl.EGameCode;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;

public class UhcHungerGameNode extends HungerGameNode {

	/**
	 * Creates a node to enable or disable the UHC mode for a game.
	 * 
	 * @param game The game associated to this node.
	 */
	protected UhcHungerGameNode(Supplier<IHungerGame> game) {
		super(game, "UHC", EHungerGameCode.HUNGER_GAME__UHC__EXPLANATION, g -> g != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(asList(getMessage(sender, EHungerGameCode.HUNGER_GAME__TRUE), getMessage(sender, EHungerGameCode.HUNGER_GAME__FALSE)).stream(), args);
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		boolean value;
		try {
			if (args[0].equalsIgnoreCase(Boolean.TRUE.toString()))
				value = true;
			else if (args[0].equalsIgnoreCase(Boolean.FALSE.toString()))
				value = false;
			else {
				send(eventBuilder(sender, EGameCode.BAD_FORMAT, getMessage(sender, EHungerGameCode.HUNGER_GAME__BOOLEAN_BAD_FORMAT)));
				return false;
			}
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__UHC__VALUE_IS_MISSING, getGame().getName()));
			return false;
		}

		getGame().getUhc().set(value);
		EHungerGameCode result = getGame().getUhc().get() ? EHungerGameCode.HUNGER_GAME__UHC__UHC_ENABLED : EHungerGameCode.HUNGER_GAME__UHC__UHC_DISABLED;
		sendSuccessful(sender, result, getGame().getName());
		return true;
	}
}
