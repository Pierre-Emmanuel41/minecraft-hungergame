package fr.pederobien.minecrafthungergame.persistence;

import fr.pederobien.minecraftgameplateform.impl.element.persistence.AbstractMinecraftPersistence;
import fr.pederobien.minecraftgameplateform.interfaces.element.persistence.IMinecraftPersistence;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.HungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.persistence.loaders.HungerGameLoaderV10;

public class HungerGamePersistence extends AbstractMinecraftPersistence<IHungerGameConfiguration> {

	private HungerGamePersistence() {
		super(Plateform.ROOT.resolve("HungerGame"), "DefaultHungerGameConfiguration");
		register(new HungerGameLoaderV10());
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
		save();
	}

	@Override
	public boolean save() {
		return true;
	}
}
