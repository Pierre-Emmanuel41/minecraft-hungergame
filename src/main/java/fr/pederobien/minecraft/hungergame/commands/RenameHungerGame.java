package fr.pederobien.minecraft.hungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonRename;

public class RenameHungerGame extends CommonRename<IHungerGameConfiguration> {

	protected RenameHungerGame() {
		super(EHungerGameMessageCode.RENAME_HG__EXPLANATION);
	}

	@Override
	protected void onNameAlreadyTaken(CommandSender sender, String currentName, String newName) {
		sendSynchro(sender, EHungerGameMessageCode.RENAME_HG__NAME_ALREADY_TAKEN, currentName, newName);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender, String oldName) {
		sendSynchro(sender, EHungerGameMessageCode.RENAME_HG__NAME_IS_MISSING, oldName);
	}

	@Override
	protected void onRenamed(CommandSender sender, String oldName, String newName) {
		sendSynchro(sender, EHungerGameMessageCode.RENAME_HG__CONFIGURATION_RENAMED, oldName, newName);
	}
}
