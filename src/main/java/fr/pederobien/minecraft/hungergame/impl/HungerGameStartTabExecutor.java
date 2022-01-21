package fr.pederobien.minecraft.hungergame.impl;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.commands.StartArgsNode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.WorldManager;

public class HungerGameStartTabExecutor implements TabExecutor, ICodeSender {
	private IHungerGame game;
	private StartArgsNode startArgsNode;

	/**
	 * Creates a tab executor in order to specify some arguments to start a game.
	 * 
	 * @param game The game associated to this node.
	 */
	public HungerGameStartTabExecutor(IHungerGame game) {
		this.game = game;
		startArgsNode = new StartArgsNode();
		startArgsNode.setGame(game);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return startArgsNode.onTabComplete(sender, command, alias, args);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Optional<IBorder> optOverworld = game.getBorders().getBorder(WorldManager.OVERWORLD);
		if (!optOverworld.isPresent()) {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__OVERWORLD_BORDER_IS_MISSING, game.getName()));
			return false;
		}

		startArgsNode.onCommand(sender, command, label, args);
		return true;
	}
}
