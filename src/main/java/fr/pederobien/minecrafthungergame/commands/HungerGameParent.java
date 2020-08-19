package fr.pederobien.minecrafthungergame.commands;

import org.bukkit.plugin.Plugin;

import fr.pederobien.minecraftgameplateform.impl.editions.AbstractGameBorderConfigurationParent;
import fr.pederobien.minecraftgameplateform.interfaces.editions.IMapPersistenceEdition;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.persistence.HungerGamePersistence;

public class HungerGameParent extends AbstractGameBorderConfigurationParent<IHungerGameConfiguration> {

	public HungerGameParent(Plugin plugin) {
		super("hg", EHungerGameMessageCode.HG_EXPLANATION, plugin, HungerGamePersistence.getInstance());
		addEdition(HungerGameEditionFactory.playerDontReviveTime());
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getNewEdition() {
		return HungerGameEditionFactory.newHungerGame().setModifiable(false);
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
		return HungerGameEditionFactory.listHungerGame().setModifiable(false);
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getDeleteEdition() {
		return HungerGameEditionFactory.deleteHungerGame().setModifiable(false);
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getDetailsEdition() {
		return HungerGameEditionFactory.detailsHungerGame();
	}

	@Override
	protected IMapPersistenceEdition<IHungerGameConfiguration> getLoadEdition() {
		return HungerGameEditionFactory.loadHungerGame().setModifiable(false);
	}
}
