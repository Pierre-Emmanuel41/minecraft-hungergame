package fr.pederobien.minecrafthungergame.commands;

import fr.pederobien.minecraftgameplateform.commands.common.CommonCurrent;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class CurrentHungerGame extends CommonCurrent<IHungerGameConfiguration> {

	protected CurrentHungerGame() {
		super(EHungerGameMessageCode.CURRENT_HG__EXPLANATION, EHungerGameMessageCode.CURRENT_HG__ON_CURRENT);
	}
}
