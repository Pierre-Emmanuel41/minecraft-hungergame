package fr.pederobien.minecraft.hungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonList;

public class ListHungerGame extends CommonList<IHungerGameConfiguration> {

	protected ListHungerGame() {
		super(EHungerGameMessageCode.LIST_HG__EXPLANATION);
	}

	@Override
	protected void onNoElement(CommandSender sender) {
		sendSynchro(sender, EHungerGameMessageCode.LIST_HG__NO_REGISTERED_CONFIGURATION);
	}

	@Override
	protected void onOneElement(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.LIST_HG__ONE_REGISTERED_CONFIGURATION, name);
	}

	@Override
	protected void onSeveralElement(CommandSender sender, String names) {
		sendSynchro(sender, EHungerGameMessageCode.LIST_HG__SEVERAL_ELEMENTS, names);
	}
}
