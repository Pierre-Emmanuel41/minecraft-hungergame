package fr.pederobien.minecraft.hungergame.commands;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftgameplateform.commands.common.CommonDetails;

public class DetailsHungerGame extends CommonDetails<IHungerGameConfiguration> {

	protected DetailsHungerGame() {
		super(EHungerGameMessageCode.DETAILS_HG__EXPLANATION, EHungerGameMessageCode.DETAILS_HG__ON_DETAILS);
	}
}
