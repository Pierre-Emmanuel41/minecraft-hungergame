package fr.pederobien.minecrafthungergame.persistence;

import fr.pederobien.minecraftgameplateform.impl.element.persistence.AbstractDefaultContent;

public class DefaultHungerGameContent extends AbstractDefaultContent {

	public DefaultHungerGameContent() {
		super("DefaultHungerGameConfiguration");
	}

	@Override
	public String getDefaultContent() {
		return "";
	}
}
