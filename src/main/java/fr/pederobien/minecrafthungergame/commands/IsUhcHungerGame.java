package fr.pederobien.minecrafthungergame.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftdevelopmenttoolkit.exceptions.BooleanParseException;
import fr.pederobien.minecraftgameplateform.dictionary.ECommonMessageCode;
import fr.pederobien.minecraftgameplateform.impl.editions.AbstractLabelEdition;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class IsUhcHungerGame extends AbstractLabelEdition<IHungerGameConfiguration> {

	protected IsUhcHungerGame() {
		super(EHungerGameLabel.IS_UHC, EHungerGameMessageCode.IS_UHC_HG__EXPLANATION);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		try {
			get().setIsUhc(getBoolean(args[0]));
			sendSynchro(sender, EHungerGameMessageCode.IS_UHC_HG__VALUE_DEFINED, get().getName(), get().isUhc());
		} catch (IndexOutOfBoundsException e) {
			sendNotSynchro(sender, EHungerGameMessageCode.IS_UHC_HG__VALUE_IS_MISSING);
			return false;
		} catch (BooleanParseException e) {
			sendNotSynchro(sender, ECommonMessageCode.COMMON_BAD_BOOLEAN_FORMAT);
			return false;
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(asList("true", "false").stream(), args);
		default:
			return emptyList();
		}
	}
}
