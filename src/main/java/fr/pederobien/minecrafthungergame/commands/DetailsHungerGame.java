package fr.pederobien.minecrafthungergame.commands;

import fr.pederobien.minecraftgameplateform.commands.common.CommonDetails;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class DetailsHungerGame extends CommonDetails<IHungerGameConfiguration> {

	protected DetailsHungerGame() {
		super(EHungerGameMessageCode.DETAILS_HG__EXPLANATION, EHungerGameMessageCode.DETAILS_HG__ON_DETAILS);
	}
}
