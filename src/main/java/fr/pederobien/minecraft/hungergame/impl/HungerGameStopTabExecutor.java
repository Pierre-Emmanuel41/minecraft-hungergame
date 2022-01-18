package fr.pederobien.minecraft.hungergame.impl;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.hungergame.commands.StopArgsNode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;

public class HungerGameStopTabExecutor implements TabExecutor {
	private StopArgsNode startArgsNode;

	/**
	 * Creates a tab executor in order to specify some arguments to stop a game.
	 * 
	 * @param game The game associated to this node.
	 */
	public HungerGameStopTabExecutor(IHungerGame game) {
		startArgsNode = new StopArgsNode();
		startArgsNode.setGame(game);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return startArgsNode.onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		startArgsNode.onCommand(sender, command, label, args);
		return true;
	}
}
