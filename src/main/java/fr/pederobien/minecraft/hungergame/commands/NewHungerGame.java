package fr.pederobien.minecraft.hungergame.commands;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.impl.HungerGameConfiguration;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonNew;
import fr.pederobien.minecraftgameplateform.commands.common.ECommonLabel;
import fr.pederobien.minecraftgameplateform.commands.configurations.EGameConfigurationLabel;
import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public class NewHungerGame extends CommonNew<IHungerGameConfiguration> {

	protected NewHungerGame() {
		super(EHungerGameMessageCode.NEW_HG__EXPLANATION);
	}

	@Override
	protected void onNameAlreadyTaken(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.NEW_HG__NAME_ALREADY_TAKEN, name);
	}

	@Override
	protected void onNameIsMissing(CommandSender sender) {
		sendSynchro(sender, EHungerGameMessageCode.NEW_HG__NAME_IS_MISSING);
	}

	@Override
	protected IHungerGameConfiguration create(String name) {
		return new HungerGameConfiguration(name);
	}

	@Override
	protected void onCreated(CommandSender sender, String name) {
		sendSynchro(sender, EHungerGameMessageCode.NEW_HG__CONFIGURATION_CREATED, name);
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
