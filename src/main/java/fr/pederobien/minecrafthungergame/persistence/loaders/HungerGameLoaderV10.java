package fr.pederobien.minecrafthungergame.persistence.loaders;

import java.io.FileNotFoundException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pederobien.minecraftgameplateform.impl.element.PlateformTeam;
import fr.pederobien.minecraftgameplateform.interfaces.element.IBorderConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftgameplateform.utils.EColor;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.persistence.HungerGameXmlTag;
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
		Node name = getElementsByTagName(root, HungerGameXmlTag.NAME).item(0);
		get().setName(name.getChildNodes().item(0).getNodeValue());

		get().clearBorders();
		// Getting border configurations
		NodeList borders = getElementsByTagName(root, HungerGameXmlTag.BORDER);
		for (int i = 0; i < borders.getLength(); i++) {
			try {
				getBorderPersistence().load(getStringAttribute((Element) borders.item(i), HungerGameXmlTag.NAME));
				get().add(getBorderPersistence().get());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		// Getting configuration times
		Node times = getElementsByTagName(root, HungerGameXmlTag.TIMES).item(0);
		get().setPvpTime(getLocalTimeAttribute((Element) times, HungerGameXmlTag.PVP));
		get().setPlayerDontReviveTime(getLocalTimeAttribute((Element) times, HungerGameXmlTag.PLAYER_DONT_REVIVE));

		get().clearTeams();
		// Getting configuration teams
		NodeList teams = getElementsByTagName(root, HungerGameXmlTag.TEAM);
		for (int i = 0; i < teams.getLength(); i++) {
			Element t = (Element) teams.item(i);
			ITeam team = PlateformTeam.of(getStringAttribute(t, HungerGameXmlTag.NAME), EColor.getByColorName(getStringAttribute(t, HungerGameXmlTag.COLOR)));
			get().add(team);
		}
		return this;
	}
}
