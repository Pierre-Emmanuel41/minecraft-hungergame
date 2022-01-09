package fr.pederobien.minecraft.hungergame.commands;

import java.util.List;
import java.util.function.Supplier;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.interfaces.IConfigurable;

public class ItemOnPlayerKillsHungerGameNode extends HungerGameNode {

	/**
	 * Creates a node to set the item to given to a player that kills another player.
	 * 
	 * @param game The game associated to this node.
	 */
	protected ItemOnPlayerKillsHungerGameNode(Supplier<IHungerGame> game) {
		super(game, "itemOnPlayerKills", EHungerGameCode.HUNGER_GAME__ITEM_ON_PLAYER_KILLS__EXPLANATION, g -> g != null);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 1:
			return filter(asList(Material.values()).stream().map(material -> material.getKey().getKey()), args);
		case 2:
			return asList(getMessage(sender, EHungerGameCode.HUNGER_GAME__ITEM_ON_PLAYER_KILLS__AMOUNT__COMPLETION));
		default:
			return emptyList();
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Material material = null;
		try {
			for (Material m : Material.values())
				if (m.getKey().getKey().equals(args[0])) {
					material = m;
					break;
				}
		} catch (IndexOutOfBoundsException e) {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__ITEM_ON_PLAYER_KILLS__ITEM_IS_MISSING, getGame().getName()));
			return false;
		}

		if (material == null) {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__ITEM_ON_PLAYER_KILLS__ITEM_NOT_FOUND, args[0]));
			return false;
		}

		int amount;
		try {
			amount = getInt(args[1]);
		} catch (IndexOutOfBoundsException | NumberFormatException e) {
			amount = 1;
		}

		IConfigurable<ItemStack> item = getGame().getItemOnPlayerKills();
		item.set(new ItemStack(material, amount));
		String itemFormat = String.format("%s:%s", normalizeMaterial(item.get().getType()), item.get().getAmount());
		sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__ITEM_ON_PLAYER_KILLS__ITEM_UPDATED, getGame().getName(), itemFormat);
		return true;
	}

	private String normalizeMaterial(Material material) {
		return material.name().toLowerCase().replace("_", " ");
	}
}
