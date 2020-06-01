package fr.pederobien.minecrafthungergame;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraftgameplateform.commands.AbstractParentCommand;
import fr.pederobien.minecrafthungergame.commands.HungerGameParent;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;

public class HungerGameCommand extends AbstractParentCommand<IHungerGameConfiguration> {

	protected HungerGameCommand(JavaPlugin plugin) {
		super(plugin, new HungerGameParent(plugin));
	}
}
