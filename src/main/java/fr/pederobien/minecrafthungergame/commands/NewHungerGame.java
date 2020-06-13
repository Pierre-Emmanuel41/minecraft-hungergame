package fr.pederobien.minecrafthungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftgameplateform.commands.common.CommonNew;
import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftgameplateform.commands.configurations.EGameConfigurationLabel;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.HungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class NewHungerGame extends CommonNew<IHungerGameConfiguration> {

	protected NewHungerGame() {
		super(EHungerGameMessageCode.NEW_HG__EXPLANATION);
	}

	@Override
	protected void onNameAlreadyTaken(CommandSender sender, String name) {
		sendMessageToSender(sender, EHungerGameMessageCode.NEW_HG__NAME_ALREADY_TAKEN, name);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendMessageToSender(sender, EHungerGameMessageCode.NEW_HG__NAME_IS_MISSING);
	}

	@Override
	protected IHungerGameConfiguration create(String name) {
		return new HungerGameConfiguration(name);
	}

	@Override
	protected void onCreated(CommandSender sender, String name) {
		sendMessageToSender(sender, EHungerGameMessageCode.NEW_HG__CONFIGURATION_CREATED, name);
		setAllAvailable();
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
