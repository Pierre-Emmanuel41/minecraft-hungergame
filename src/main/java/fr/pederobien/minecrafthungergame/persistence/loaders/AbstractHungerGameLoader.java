package fr.pederobien.minecrafthungergame.persistence.loaders;

import fr.pederobien.minecrafthungergame.HungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistenceLoader;

public abstract class AbstractHungerGameLoader extends AbstractXmlPersistenceLoader<IHungerGameConfiguration> {

	protected AbstractHungerGameLoader(Double version) {
		super(version, new HungerGameConfiguration("DefaultHungerGameConfiguration"));
	}
}
