package fr.pederobien.minecraft.hungergame.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import fr.pederobien.minecraft.border.interfaces.IBorder;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.WorldManager;

public class HungerGameStartTabExecutor implements TabExecutor, ICodeSender {
	private IHungerGame game;

	public HungerGameStartTabExecutor(IHungerGame game) {
		this.game = game;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		Optional<IBorder> optOverworld = game.getBorders().getBorder(WorldManager.OVERWORLD);
		if (!optOverworld.isPresent())
			return new ArrayList<String>();

		return new ArrayList<String>();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!checkOverWorldBorder(sender))
			return false;
		return true;
	}

	private boolean checkOverWorldBorder(CommandSender sender) {
		Optional<IBorder> optOverworld = game.getBorders().getBorder(WorldManager.OVERWORLD);
		if (!optOverworld.isPresent()) {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__OVERWORLD_BORDER_IS_MISSING, game.getName()));
			return false;
		}
		return true;
	}
}
