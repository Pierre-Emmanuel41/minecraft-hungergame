package fr.pederobien.minecraft.hungergame;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.dictionary.impl.JarXmlDictionaryParser;
import fr.pederobien.minecraft.dictionary.impl.MinecraftDictionaryContext;
import fr.pederobien.minecraft.hungergame.commands.HungerGameCommandTree;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.persistence.HungerGamePersistence;
import fr.pederobien.minecraft.platform.GamePlatformPlugin;
import fr.pederobien.minecraft.platform.interfaces.IPlatformPersistence;
import fr.pederobien.utils.AsyncConsole;

public class HGPlugin extends JavaPlugin {
	private static final String DICTIONARY_FOLDER = "resources/dictionaries/";

	private static Plugin instance;
	private static IPlatformPersistence<IHungerGame> persistence;
	private static HungerGameCommandTree hungerGameTree;

	/**
	 * @return The instance of this plugin.
	 */
	public static Plugin instance() {
		return instance;
	}

	/**
	 * @return The persistence that serialize and deserialize hunger game configurations.
	 */
	public static IPlatformPersistence<IHungerGame> getPersistence() {
		return persistence;
	}

	/**
	 * Get the tree to modify the characteristics of an hunger game.
	 * 
	 * @return The hunger game tree associated to this plugin.
	 */
	public static HungerGameCommandTree getHungerGameTree() {
		return hungerGameTree;
	}

	@Override
	public void onEnable() {
		instance = this;
		persistence = new HungerGamePersistence().getPersistence();
		hungerGameTree = new HungerGameCommandTree(persistence);

		registerDictionaries();
		registerTabExecutors();
	}

	@Override
	public void onDisable() {
		if (GamePlatformPlugin.getGameTree().getGame() != null)
			GamePlatformPlugin.getGameTree().getGame().stop();
	}

	private void registerDictionaries() {
		JarXmlDictionaryParser dictionaryParser = new JarXmlDictionaryParser(getFile().toPath());

		MinecraftDictionaryContext context = MinecraftDictionaryContext.instance();
		String[] dictionaries = new String[] { "English.xml", "French.xml" };
		for (String dictionary : dictionaries)
			try {
				context.register(dictionaryParser.parse(DICTIONARY_FOLDER.concat(dictionary)));
			} catch (Exception e) {
				AsyncConsole.print(e);
				for (StackTraceElement element : e.getStackTrace())
					AsyncConsole.print(element);
			}
	}

	private void registerTabExecutors() {
		PluginCommand hg = getCommand(hungerGameTree.getRoot().getLabel());
		hg.setTabCompleter(hungerGameTree.getRoot());
		hg.setExecutor(hungerGameTree.getRoot());
	}
}
