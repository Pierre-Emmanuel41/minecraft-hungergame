package fr.pederobien.minecrafthungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.CommonList;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class ListHungerGame extends CommonList<IHungerGameConfiguration> {

	protected ListHungerGame() {
		super(EHungerGameMessageCode.LIST_HG__EXPLANATION);
	}

	@Override
	protected void onNoElement(CommandSender sender) {
		sendMessageToSender(sender, EHungerGameMessageCode.LIST_HG__NO_REGISTERED_CONFIGURATION);
	}

	@Override
	protected void onOneElement(CommandSender sender, String name) {
		sendMessageToSender(sender, EHungerGameMessageCode.LIST_HG__ONE_REGISTERED_CONFIGURATION, name);
	}

	@Override
	protected void onSeveralElement(CommandSender sender, String names) {
		sendMessageToSender(sender, EHungerGameMessageCode.LIST_HG__SEVERAL_ELEMENTS, names);
	}
}
