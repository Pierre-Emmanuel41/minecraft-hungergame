package fr.pederobien.minecrafthungergame.persistence;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.pederobien.minecraftborder.interfaces.IBorderConfiguration;
import fr.pederobien.minecraftborder.persistence.BorderPersistence;
import fr.pederobien.minecraftgameplateform.impl.element.PlateformTeam;
import fr.pederobien.minecraftgameplateform.impl.element.persistence.AbstractMinecraftPersistence;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.interfaces.element.persistence.IMinecraftPersistence;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.impl.HungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.persistence.loaders.HungerGameLoaderV10;
import fr.pederobien.minecrafthungergame.persistence.loaders.HungerGameLoaderV11;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.persistence.interfaces.IPersistence;

public class HungerGamePersistence extends AbstractMinecraftPersistence<IHungerGameConfiguration> {
	private static final String ROOT_XML_DOCUMENT = "hungergame";
	private IPersistence<IBorderConfiguration> borderPersistence;

	private HungerGamePersistence() {
		super(Plateform.ROOT.resolve("HungerGame"), "DefaultHungerGameConfiguration");
		borderPersistence = BorderPersistence.getInstance();
		register(new HungerGameLoaderV10(borderPersistence)).register(new HungerGameLoaderV11(borderPersistence));
	}

	public static IMinecraftPersistence<IHungerGameConfiguration> getInstance() {
		return SingletonHolder.PERSISTENCE;
	}

	private static class SingletonHolder {
		public static final IMinecraftPersistence<IHungerGameConfiguration> PERSISTENCE = new HungerGamePersistence();
	}

	@Override
	public void saveDefault() {
		set(new HungerGameConfiguration(getDefault()));
		get().add(PlateformTeam.of("knights", EColor.DARK_AQUA));
		get().add(PlateformTeam.of("vikings", EColor.GREEN));
		get().add(PlateformTeam.of("barbarics", EColor.DARK_RED));
		get().add(PlateformTeam.of("spartiates", EColor.GOLD));
		save();
		for (IBorderConfiguration border : get().getBorders()) {
			borderPersistence.set(border);
			borderPersistence.save();
		}
		borderPersistence.set(null);
	}

	@Override
	public boolean save() {
		if (get() == null)
			return false;
		Document doc = newDocument();
		doc.setXmlStandalone(true);

		Element root = createElement(doc, ROOT_XML_DOCUMENT);
		doc.appendChild(root);

		Element version = createElement(doc, VERSION);
		version.appendChild(doc.createTextNode(getVersion().toString()));
		root.appendChild(version);

		Element name = createElement(doc, HungerGameXmlTag.NAME);
		name.appendChild(doc.createTextNode(get().getName()));
		root.appendChild(name);

		Element borders = createElement(doc, HungerGameXmlTag.BORDERS);
		for (IBorderConfiguration configuration : get().getBorders()) {
			Element border = createElement(doc, HungerGameXmlTag.BORDER);
			setAttribute(border, HungerGameXmlTag.NAME, configuration.getName());
			borders.appendChild(border);
		}
		root.appendChild(borders);

		Element isUhc = createElement(doc, HungerGameXmlTag.IS_UHC);
		isUhc.appendChild(doc.createTextNode(get().isUhc().toString()));
		root.appendChild(isUhc);

		Element times = createElement(doc, HungerGameXmlTag.TIMES);
		setAttribute(times, HungerGameXmlTag.PVP, get().getPvpTime());
		setAttribute(times, HungerGameXmlTag.PLAYER_DONT_REVIVE, get().getPlayerDontReviveTime());
		root.appendChild(times);

		Element teams = createElement(doc, HungerGameXmlTag.TEAMS);
		for (ITeam t : get().getTeams()) {
			Element team = createElement(doc, HungerGameXmlTag.TEAM);
			setAttribute(team, HungerGameXmlTag.NAME, t.getName());
			setAttribute(team, HungerGameXmlTag.COLOR, t.getColor());
			teams.appendChild(team);
		}
		root.appendChild(teams);

		saveDocument(doc, get().getName());
		return true;
	}
}
