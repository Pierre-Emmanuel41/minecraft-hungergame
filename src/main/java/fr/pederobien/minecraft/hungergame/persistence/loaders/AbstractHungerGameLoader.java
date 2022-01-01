package fr.pederobien.minecraft.hungergame.persistence.loaders;

import java.io.FileNotFoundException;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.pederobien.minecraft.hungergame.impl.HungerGameConfiguration;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraft.hungergame.persistence.HungerGameXmlTag;
import fr.pederobien.minecraftborder.interfaces.IBorderConfiguration;
import fr.pederobien.minecraftgameplateform.impl.element.PlateformTeam;
import fr.pederobien.minecraftgameplateform.interfaces.element.ITeam;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.persistence.impl.xml.AbstractXmlPersistenceLoader;
import fr.pederobien.persistence.interfaces.IPersistence;

public abstract class AbstractHungerGameLoader extends AbstractXmlPersistenceLoader<IHungerGameConfiguration> {
	private IPersistence<IBorderConfiguration> borderPersistence;

	protected AbstractHungerGameLoader(Double version, IPersistence<IBorderConfiguration> borderPersistence) {
		super(version);
		this.borderPersistence = borderPersistence;
	}

	@Override
	protected IHungerGameConfiguration create() {
		return new HungerGameConfiguration("DefaultHungerGameConfiguration");
	}

	/**
	 * @return The persistence that load borders associated to an hunger game configuration.
	 */
	public IPersistence<IBorderConfiguration> getBorderPersistence() {
		return borderPersistence;
	}

	/**
	 * Set the configuration's name
	 * 
	 * @param root The xml root that contains all configuration's parameter
	 */
	protected void setName(Element root) {
		Node name = getElementsByTagName(root, HungerGameXmlTag.NAME).item(0);
		get().setName(name.getChildNodes().item(0).getNodeValue());
	}

	/**
	 * Set the configuration's borders
	 * 
	 * @param root The xml root that contains all configuration's parameter
	 */
	protected void setBorders(Element root) {
		get().clearBorders();
		NodeList borders = getElementsByTagName(root, HungerGameXmlTag.BORDER);
		for (int i = 0; i < borders.getLength(); i++) {
			try {
				getBorderPersistence().load(getStringAttribute((Element) borders.item(i), HungerGameXmlTag.NAME));
				get().add(getBorderPersistence().get());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Set the configuration's times : pvpTime, playerDontReviveTimes.
	 * 
	 * @param root The xml root that contains all configuration's parameter
	 */
	protected void setTimes(Element root) {
		Node times = getElementsByTagName(root, HungerGameXmlTag.TIMES).item(0);
		get().setPvpTime(getLocalTimeAttribute((Element) times, HungerGameXmlTag.PVP));
		get().setPlayerDontReviveTime(getLocalTimeAttribute((Element) times, HungerGameXmlTag.PLAYER_DONT_REVIVE));
	}

	/**
	 * Set the configuration's teams.
	 * 
	 * @param root The xml root that contains all configuration's parameter
	 */
	protected void setTeams(Element root) {
		get().clearTeams();
		NodeList teams = getElementsByTagName(root, HungerGameXmlTag.TEAM);
		for (int i = 0; i < teams.getLength(); i++) {
			Element t = (Element) teams.item(i);
			ITeam team = PlateformTeam.of(getStringAttribute(t, HungerGameXmlTag.NAME), EColor.getByColorName(getStringAttribute(t, HungerGameXmlTag.COLOR)));
			get().add(team);
		}
	}

	/**
	 * Set the configuration's uhc mode.
	 * 
	 * @param root The xml root that contains all configuration's parameter
	 */
	protected void setIsUhc(Element root) {
		Node isUhc = getElementsByTagName(root, HungerGameXmlTag.IS_UHC).item(0);
		get().setIsUhc(getBooleanNodeValue(isUhc.getChildNodes().item(0)));
	}

	/**
	 * Set the configuration's item on player kills.
	 * 
	 * @param root The xml root that contains all configuration's parameter
	 */
	protected void setItemOnPlayerKills(Element root) {
		Node itemOnPlayerKills = getElementsByTagName(root, HungerGameXmlTag.ITEM_ON_PLAYER_KILLS).item(0);
		get().setItemOnPlayerKills(new ItemStack(getMaterial(itemOnPlayerKills.getChildNodes().item(0).getNodeValue())));
	}

	private Material getMaterial(String name) {
		String normalizedName = name.toUpperCase().replace(" ", "_");
		for (Material material : Material.values())
			if (material.name().equals(normalizedName))
				return material;
		return null;
	}
}
