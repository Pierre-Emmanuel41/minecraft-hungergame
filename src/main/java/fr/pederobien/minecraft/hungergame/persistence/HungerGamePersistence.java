package fr.pederobien.minecraft.hungergame.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.border.persistence.BorderPersistence;
import fr.pederobien.minecraft.hungergame.impl.HungerGame;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.persistence.loaders.HungerGameSerializerV10;
import fr.pederobien.minecraft.hungergame.persistence.loaders.HungerGameSerializerV11;
import fr.pederobien.minecraft.hungergame.persistence.loaders.HungerGameSerializerV12;
import fr.pederobien.minecraft.platform.impl.PlatformPersistence;
import fr.pederobien.minecraft.platform.interfaces.IPlatformPersistence;
import fr.pederobien.persistence.impl.Persistences;
import fr.pederobien.persistence.impl.xml.XmlPersistence;

public class HungerGamePersistence {
	private static final Path HUNGER_GAME = Paths.get("HungerGame");
	private IPlatformPersistence<IBorder> borderPersistence;
	private IPlatformPersistence<IHungerGame> persistence;

	public HungerGamePersistence() {
		borderPersistence = new BorderPersistence().getPersistence();

		XmlPersistence<IHungerGame> xmlPersistence = Persistences.xmlPersistence();
		xmlPersistence.register(xmlPersistence.adapt(new HungerGameSerializerV10(borderPersistence)));
		xmlPersistence.register(xmlPersistence.adapt(new HungerGameSerializerV11(borderPersistence)));
		xmlPersistence.register(xmlPersistence.adapt(new HungerGameSerializerV12(borderPersistence)));

		// Action to save borders associated to default hunger game
		Consumer<IHungerGame> writeDefault = game -> {
			for (IBorder border : game.getBorders().toList()) {
				if (!borderPersistence.exist(border.getName())) {
					borderPersistence.set(border);
					borderPersistence.serialize();
				}
			}
		};
		persistence = new PlatformPersistence<IHungerGame>(HUNGER_GAME, name -> new HungerGame(name), xmlPersistence, writeDefault);
	}

	/**
	 * @return The platform persistence.
	 */
	public IPlatformPersistence<IHungerGame> getPersistence() {
		return persistence;
	}
}
