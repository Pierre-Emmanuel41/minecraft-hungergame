package fr.pederobien.minecraft.hungergame.commands;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftdevelopmenttoolkit.utils.DisplayHelper;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractLabelEdition;

public class PlayerDontReviveTime extends AbstractLabelEdition<IHungerGameConfiguration> {

	protected PlayerDontReviveTime() {
		super(EHungerGameLabel.PLAYER_DONT_REVIVE_TIME, EHungerGameMessageCode.PLAYER_DONT_REVIVE_TIME_HG__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			get().setPlayerDontReviveTime(LocalTime.parse(args[0]));
			if (get().getPlayerDontReviveTime().equals(LocalTime.of(0, 0, 0)))
				sendSynchro(sender, EHungerGameMessageCode.PLAYER_DONT_REVIVE_TIME_HG__FROM_THE_BEGINNING);
			else
				sendSynchro(sender, EHungerGameMessageCode.PLAYER_DONT_REVIVE_TIME_HG__TIME_DEFINED, DisplayHelper.toString(get().getPlayerDontReviveTime(), false));
			return true;
		} catch (IndexOutOfBoundsException e) {
			sendSynchro(sender, EHungerGameMessageCode.PLAYER_DONT_REVIVE_TIME_HG__TIME_IS_MISSING);
			return false;
		} catch (DateTimeParseException e) {
			sendSynchro(sender, ECommonMessageCode.COMMON_BAD_TIME_FORMAT);
			return false;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return asList(getMessage(sender, ECommonMessageCode.COMMON_TIME_TAB_COMPLETE));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean isAvailable() {
		return get() != null && !get().isUhc();
	}
}
