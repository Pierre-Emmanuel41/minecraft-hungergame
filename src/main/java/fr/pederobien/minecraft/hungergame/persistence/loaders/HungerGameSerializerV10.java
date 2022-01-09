package fr.pederobien.minecraft.hungergame.persistence.loaders;

import org.w3c.dom.Element;

import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.persistence.HungerGameXmlTag;
import fr.pederobien.minecraft.platform.interfaces.IPlatformPersistence;

public class HungerGameSerializerV10 extends AbstractHungerGameSerializer {

	public HungerGameSerializerV10(IPlatformPersistence<IBorder> borderPersistence) {
		super(1.0, borderPersistence);
	}

	@Override
	public boolean deserialize(IHungerGame element, Element root) {
		// Getting configuration name
		setName(element, root);

		// Getting border configurations
		setBorders(element, root);

		// Getting configuration times
		setTimes(element, root);

		// Getting configuration teams
		setTeams(element, root);
		return true;
	}

	@Override
	public boolean serialize(IHungerGame element, Element root) {
		Element name = createElement(HungerGameXmlTag.NAME);
		name.appendChild(createTextNode(element.getName()));
		root.appendChild(name);

		Element borders = createElement(HungerGameXmlTag.BORDERS);
		for (IBorder borderConf : element.getBorders().toList()) {
			Element border = createElement(HungerGameXmlTag.BORDER);
			setAttribute(border, HungerGameXmlTag.NAME, borderConf.getName());
			borders.appendChild(border);
		}
		root.appendChild(borders);

		Element times = createElement(HungerGameXmlTag.TIMES);
		setAttribute(times, HungerGameXmlTag.PVP, element.getPvpTime());
		setAttribute(times, HungerGameXmlTag.PLAYER_DONT_REVIVE, element.getPlayerDontReviveTime());
		root.appendChild(times);

		Element teams = createElement(HungerGameXmlTag.TEAMS);
		for (ITeam t : element.getTeams().toList()) {
			Element team = createElement(HungerGameXmlTag.TEAM);
			setAttribute(team, HungerGameXmlTag.NAME, t.getName());
			setAttribute(team, HungerGameXmlTag.COLOR, t.getColor());
			teams.appendChild(team);
		}
		root.appendChild(teams);
		return false;
	}
}
