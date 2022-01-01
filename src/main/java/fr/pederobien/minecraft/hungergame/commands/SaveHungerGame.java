package fr.pederobien.minecraft.hungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonSave;

public class SaveHungerGame extends CommonSave<IHungerGameConfiguration> {

	protected SaveHungerGame() {
		super(EHungerGameMessageCode.SAVE_HG__EXPLANATION);
	}

	@Override
	protected void onSave(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.SAVE_HG__CONFIGURATION_SAVED, name);
	}
}
