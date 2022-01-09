package fr.pederobien.minecraft.hungergame.persistence.loaders;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.game.impl.Team;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.persistence.HungerGameXmlTag;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.platform.interfaces.IPlatformPersistence;
import fr.pederobien.minecraft.rules.interfaces.IRule;
import fr.pederobien.persistence.impl.xml.AbstractXmlSerializer;

public abstract class AbstractHungerGameSerializer extends AbstractXmlSerializer<IHungerGame> {
	private IPlatformPersistence<IBorder> borderPersistence;

	protected AbstractHungerGameSerializer(Double version, IPlatformPersistence<IBorder> borderPersistence) {
		super(version);
		this.borderPersistence = borderPersistence;
	}

	/**
	 * @return The persistence that load borders associated to an hunger game configuration.
	 */
	public IPlatformPersistence<IBorder> getBorderPersistence() {
		return borderPersistence;
	}

	/**
	 * Set the configuration's name
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains game parameters
	 */
	protected void setName(IHungerGame element, Element root) {
		Node name = getElementsByTagName(root, HungerGameXmlTag.NAME).item(0);
		element.setName(name.getChildNodes().item(0).getNodeValue());
	}

	/**
	 * Set the configuration's borders
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains game parameters.
	 */
	protected void setBorders(IHungerGame element, Element root) {
		element.getBorders().clear();
		NodeList borders = getElementsByTagName(root, HungerGameXmlTag.BORDER);
		for (int i = 0; i < borders.getLength(); i++) {
			getBorderPersistence().deserialize(getStringAttribute((Element) borders.item(i), HungerGameXmlTag.NAME));
			element.getBorders().add(getBorderPersistence().get());
		}
	}

	/**
	 * Set the configuration's times : pvpTime, playerDontReviveTimes.
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains all configuration's parameter
	 */
	protected void setTimes(IHungerGame element, Element root) {
		Node times = getElementsByTagName(root, HungerGameXmlTag.TIMES).item(0);
		element.getPvpTime().set(getLocalTimeAttribute((Element) times, HungerGameXmlTag.PVP));
		element.getPlayerDontReviveTime().set(getLocalTimeAttribute((Element) times, HungerGameXmlTag.PLAYER_DONT_REVIVE));
	}

	/**
	 * Set the configuration's teams.
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains all configuration's parameter
	 */
	protected void setTeams(IHungerGame element, Element root) {
		element.getTeams().clear();
		NodeList teams = getElementsByTagName(root, HungerGameXmlTag.TEAM);
		for (int i = 0; i < teams.getLength(); i++) {
			Element t = (Element) teams.item(i);
			ITeam team = Team.of(getStringAttribute(t, HungerGameXmlTag.NAME), EColor.getByColorName(getStringAttribute(t, HungerGameXmlTag.COLOR)));
			element.getTeams().add(team);
		}
	}

	/**
	 * Set the configuration's uhc mode.
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains all configuration's parameter
	 */
	protected void setIsUhc(IHungerGame element, Element root) {
		Node isUhc = getElementsByTagName(root, HungerGameXmlTag.IS_UHC).item(0);
		element.getUhc().set(getBooleanNodeValue(isUhc.getChildNodes().item(0)));
	}

	/**
	 * Set the configuration's item on player kills.
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains all configuration's parameter
	 */
	protected void setItemOnPlayerKills(IHungerGame element, Element root) {
		Node itemOnPlayerKills = getElementsByTagName(root, HungerGameXmlTag.ITEM_ON_PLAYER_KILLS).item(0);
		element.getItemOnPlayerKills().set(new ItemStack(getMaterial(itemOnPlayerKills.getChildNodes().item(0).getNodeValue())));
	}

	/**
	 * Set the configuration's rules.
	 * 
	 * @param element The element to update.
	 * @param root    The XML root that contains all configuration's parameter.
	 */
	protected void setRules(IHungerGame element, Element root) {
		NodeList rules = getElementsByTagName(root, HungerGameXmlTag.RULE);
		for (int i = 0; i < rules.getLength(); i++) {
			Element ruleElement = (Element) rules.item(i);
			String name = getStringAttribute(ruleElement, HungerGameXmlTag.NAME);
			String currentValue = getStringAttribute(ruleElement, HungerGameXmlTag.CURRENT);
			Optional<IRule<Object>> optRule = element.getRules().getRule(name);
			if (optRule.isPresent())
				optRule.get().setValue(optRule.get().getParser().deserialize(currentValue));
		}
	}

	/**
	 * Replace the character "_" by " " in the name of the given material.
	 * 
	 * @param material The material whose the name should be normalized.
	 * 
	 * @return The normalized material name.
	 */
	protected String normalizeMaterial(Material material) {
		return material.name().toLowerCase().replace("_", " ");
	}

	private Material getMaterial(String name) {
		String normalizedName = name.toUpperCase().replace(" ", "_");
		for (Material material : Material.values())
			if (material.name().equals(normalizedName))
				return material;
		return null;
	}
}
