package fr.pederobien.minecraft.hungergame;

import org.bukkit.plugin.java.JavaPlugin;

import fr.pederobien.minecraft.hungergame.commands.HungerGameParent;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecraftgameplateform.commands.AbstractParentCommand;

public class HungerGameCommand extends AbstractParentCommand<IHungerGameConfiguration> {

	protected HungerGameCommand(JavaPlugin plugin) {
		super(plugin, new HungerGameParent(plugin));
	}
}
