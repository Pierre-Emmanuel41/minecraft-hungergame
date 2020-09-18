package fr.pederobien.minecrafthungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.CommonSave;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class SaveHungerGame extends CommonSave<IHungerGameConfiguration> {

	protected SaveHungerGame() {
		super(EHungerGameMessageCode.SAVE_HG__EXPLANATION);
	}

	@Override
	protected void onSave(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.SAVE_HG__CONFIGURATION_SAVED, name);
	}
}
