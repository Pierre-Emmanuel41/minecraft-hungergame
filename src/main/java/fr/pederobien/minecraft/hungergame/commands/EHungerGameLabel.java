package fr.pederobien.minecraft.hungergame.commands;

import fr.pederobien.minecraftgameplateform.interfaces.element.ILabel;

public enum EHungerGameLabel implements ILabel {
	PLAYER_DONT_REVIVE_TIME("playerDontReviveTime"), IS_UHC("isUhc"), ITEM_ON_PLAYER_KILLS("itemOnPlayerKills");

	private String label;

	private EHungerGameLabel(String label) {
		this.label = label;
	}

	@Override
	public String getLabel() {
		return label;
	}
}
