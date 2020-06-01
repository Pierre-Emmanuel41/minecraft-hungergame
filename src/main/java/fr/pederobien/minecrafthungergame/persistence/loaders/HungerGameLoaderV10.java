package fr.pederobien.minecrafthungergame.persistence.loaders;

import org.w3c.dom.Element;

import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.persistence.interfaces.xml.IXmlPersistenceLoader;

public class HungerGameLoaderV10 extends AbstractHungerGameLoader {

	public HungerGameLoaderV10() {
		super(1.0);
	}

	@Override
	public IXmlPersistenceLoader<IHungerGameConfiguration> load(Element root) {
		return this;
	}
}
