package fr.pederobien.minecrafthungergame.persistence.loaders;

import fr.pederobien.minecraftgameplateform.interfaces.element.IBorderConfiguration;
import fr.pederobien.minecrafthungergame.HungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistenceLoader;
import fr.pederobien.persistence.interfaces.IPersistence;

public abstract class AbstractHungerGameLoader extends AbstractXmlPersistenceLoader<IHungerGameConfiguration> {
	private IPersistence<IBorderConfiguration> borderPersistence;

	protected AbstractHungerGameLoader(Double version, IPersistence<IBorderConfiguration> borderPersistence) {
		super(version, new HungerGameConfiguration("DefaultHungerGameConfiguration"));
		this.borderPersistence = borderPersistence;
	}

	/**
	 * @return The persistence that load borders associated to an hunger game configuration.
	 */
	public IPersistence<IBorderConfiguration> getBorderPersistence() {
		return borderPersistence;
	}
}
