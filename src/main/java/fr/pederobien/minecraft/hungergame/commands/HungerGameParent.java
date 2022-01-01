package fr.pederobien.minecraft.hungergame.commands;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraft.hungergame.persistence.HungerGamePersistence;
import fr.pederobien.minecraftborder.editions.AbstractGameBorderConfigurationParent;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;

public class HungerGameParent extends AbstractGameBorderConfigurationParent<IHungerGameConfiguration> {

	public HungerGameParent(Plugin plugin) {
		super("hg", EHungerGameMessageCode.HG_EXPLANATION, plugin, HungerGamePersistence.getInstance());
		addEdition(HungerGameEditionFactory.playerDontReviveTime());
		addEdition(HungerGameEditionFactory.isUhc());
		addEdition(HungerGameEditionFactory.itemOnPlayerKills());
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getNewEdition() {
		return HungerGameEditionFactory.newHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getRenameEdition() {
		return HungerGameEditionFactory.renameHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getSaveEdition() {
		return HungerGameEditionFactory.saveHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getListEdition() {
		return HungerGameEditionFactory.listHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getDeleteEdition() {
		return HungerGameEditionFactory.deleteHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getDetailsEdition() {
		return HungerGameEditionFactory.detailsHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getLoadEdition() {
		return HungerGameEditionFactory.loadHungerGame();
	}
}
