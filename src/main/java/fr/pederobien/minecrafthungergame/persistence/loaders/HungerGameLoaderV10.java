package fr.pederobien.minecrafthungergame.persistence.loaders;

import org.w3c.dom.Element;

import fr.pederobien.minecraftborder.interfaces.IBorderConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.persistence.interfaces.IPersistence;
import fr.pederobien.persistence.interfaces.xml.IXmlPersistenceLoader;

public class HungerGameLoaderV10 extends AbstractHungerGameLoader {

	public HungerGameLoaderV10(IPersistence<IBorderConfiguration> borderPersistence) {
		super(1.0, borderPersistence);
	}

	@Override
	public IXmlPersistenceLoader<IHungerGameConfiguration> load(Element root) {
		createNewElement();

		// Getting configuration name
		setName(root);

		// Getting border configurations
		setBorders(root);

		// Getting configuration times
		setTimes(root);

		// Getting configuration teams
		setTeams(root);
		return this;
	}
}
