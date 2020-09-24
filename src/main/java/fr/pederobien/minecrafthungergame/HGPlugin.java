package fr.pederobien.minecrafthungergame;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.interfaces.IDictionaryParser;
import fr.pederobien.minecraftgameplateform.interfaces.commands.IParentCommand;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class HGPlugin extends JavaPlugin {
	private static IParentCommand<IHungerGameConfiguration> hungerGameCommand;
	private static Plugin plugin;

	/**
	 * @return The current hunger game configuration for this plugin.
	 */
	public static IHungerGameConfiguration getCurrentConfiguration() {
		return hungerGameCommand.getParent().get();
	}

	/**
	 * @return The plugin associated to this hunger game plugin.
	 */
	public static Plugin get() {
		return plugin;
	}

	@Override
	public void onEnable() {
		Plateform.getPluginHelper().register(this);
		plugin = this;
		hungerGameCommand = new HungerGameCommand(this);
		registerDictionaries();
	}

	private void registerDictionaries() {
		// Registering French dictionaries
		registerDictionary("French", "HungerGame.xml");

		// Registering English dictionaries
		registerDictionary("English", "HungerGame.xml");
	}

	private void registerDictionary(String parent, String... dictionaryNames) {
		Path jarPath = Plateform.ROOT.getParent().resolve(getName().concat(".jar"));
		String dictionariesFolder = "resources/dictionaries/".concat(parent).concat("/");
		for (String name : dictionaryNames)
			registerDictionary(Plateform.getDefaultDictionaryParser(dictionariesFolder.concat(name)), jarPath);
	}

	private void registerDictionary(IDictionaryParser parser, Path jarPath) {
		try {
			Plateform.getNotificationCenter().getDictionaryContext().register(parser, jarPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
