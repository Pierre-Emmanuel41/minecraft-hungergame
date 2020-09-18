package fr.pederobien.minecrafthungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.CommonLoad;
import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftgameplateform.commands.configurations.EGameConfigurationLabel;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class LoadHungerGame extends CommonLoad<IHungerGameConfiguration> {

	protected LoadHungerGame() {
		super(EHungerGameMessageCode.LOAD_HG__EXPLANATION);
	}

	@Override
	protected void onStyleLoaded(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.LOAD_HG__CONFIGURATION_LOADED, name);
		setAllAvailable();
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendSynchro(sender, EHungerGameMessageCode.LOAD_HG__NAME_IS_MISSING);
	}

	private void setAllAvailable() {
		for (ILabel label : ECommonLabel.values())
			setAvailableLabelEdition(label);
		for (ILabel label : EGameConfigurationLabel.values())
			setAvailableLabelEdition(label);
		for (ILabel label : EHungerGameLabel.values())
			setAvailableLabelEdition(label);
	}
}
