package fr.pederobien.minecrafthungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.CommonDelete;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class DeleteHungerGame extends CommonDelete<IHungerGameConfiguration> {

	protected DeleteHungerGame() {
		super(EHungerGameMessageCode.DELETE_HG__EXPLANATION);
	}

	@Override
	protected void onDidNotDelete(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.DELETE_HG__DID_NOT_DELETE, name);
	}

	@Override
	protected void onDeleted(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.DELETE_HG__CONFIGURATION_DELETED, name);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendSynchro(sender, EHungerGameMessageCode.DELETE_HG__NAME_IS_MISSING);
	}
}
