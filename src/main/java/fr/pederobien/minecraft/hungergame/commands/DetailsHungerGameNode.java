package fr.pederobien.minecraft.hungergame.commands;

import java.util.StringJoiner;
import java.util.function.BiConsumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.border.commands.borders.DetailsBordersNode;
import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.game.impl.DisplayHelper;
import fr.pederobien.minecraft.game.interfaces.ITeam;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.EColor;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceDetailsNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;
import fr.pederobien.minecraft.rules.interfaces.IRule;

public class DetailsHungerGameNode implements ICodeSender {
	private DetailsBordersNode detailsBorderNode;
	private PersistenceDetailsNode<IHungerGame> detailsNode;

	protected DetailsHungerGameNode(PersistenceNodeFactory<IHungerGame> factory, DetailsBordersNode detailsBorderNode) {
		this.detailsBorderNode = detailsBorderNode;

		// Action in order to display the details of the hunger game
		BiConsumer<CommandSender, IHungerGame> onDetails = (sender, game) -> {
			String details = getDetails(sender, game);
			StringJoiner detailsJoiner = new StringJoiner("\n");
			String[] lines = details.split("\n");
			for (String line : lines)
				detailsJoiner.add(color(line));

			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__DETAILS_GAME__ON_DETAILS, detailsJoiner);
		};

		// Creating the node that displays the details of the current hunger game.
		detailsNode = factory.detailsNode(onDetails).build(EHungerGameCode.HUNGER_GAME__DETAILS_GAME__EXPLANATION);

		// Node available if and only if the current hunger game is not null
		detailsNode.setAvailable(() -> factory.getPersistence().get() != null);
	}

	/**
	 * @return The node that display the details of the hunger game.
	 */
	public PersistenceDetailsNode<IHungerGame> getNode() {
		return detailsNode;
	}

	/**
	 * Get the details of an hunger game. Each parameter name are translated according to the sender nationality.
	 * 
	 * @param sender The sender used to translate parameter name.
	 * @param game   The game whose the details should be returned.
	 * 
	 * @return The hunger game details.
	 */
	public String getDetails(CommandSender sender, IHungerGame game) {
		String name = getMessage(sender, EHungerGameCode.HUNGER_GAME_NAME, game.getName());

		String teams;
		if (game.getTeams().toList().isEmpty())
			teams = getMessage(sender, EHungerGameCode.HUNGER_GAME_TEAMS, getMessage(sender, EHungerGameCode.HUNGER_GAME_NONE));
		else {
			StringJoiner teamJoiner = new StringJoiner("\n");
			for (ITeam team : game.getTeams().toList())
				teamJoiner.add(team.toString());
			teams = getMessage(sender, EHungerGameCode.HUNGER_GAME_TEAMS, String.format("%n%s", teamJoiner));
		}

		String borders = detailsBorderNode.getDetails(sender, game.getBorders().toList());

		String uhcValue = getMessage(sender, game.getUhc().get() ? EHungerGameCode.HUNGER_GAME__TRUE : EHungerGameCode.HUNGER_GAME__FALSE);
		String uhc = getMessage(sender, EHungerGameCode.HUNGER_GAME_UHC, uhcValue);

		String playerDontReviveTimeFormat = DisplayHelper.toString(game.getPlayerDontReviveTime().get(), true);
		String playerDontReviveTime = getMessage(sender, EHungerGameCode.HUNGER_GAME_PLAYER_DONT_REVIVE_TIME, playerDontReviveTimeFormat);

		String pvpTimeFormat = DisplayHelper.toString(game.getPvpTime().get(), true);
		String pvpTime = getMessage(sender, EHungerGameCode.HUNGER_GAME_PVP_TIME, pvpTimeFormat);

		String itemOnPlayerKillsFormat = DisplayHelper.toString(game.getItemOnPlayerKills().get().getType(), true);
		String itemOnPlayerKills = getMessage(sender, EHungerGameCode.HUNGER_GAME_ITEM_ON_PLAYER_KILLS, itemOnPlayerKillsFormat);

		String rules;
		if (game.getRules().toList().isEmpty())
			rules = getMessage(sender, EHungerGameCode.HUNGER_GAME__RULES, getMessage(sender, EHungerGameCode.HUNGER_GAME_NONE));
		else {
			StringJoiner ruleJoiner = new StringJoiner("\n");
			for (IRule<?> rule : game.getRules().toList())
				ruleJoiner.add(getMessage(sender, EHungerGameCode.HUNGER_GAME__RULE, rule.getName(), rule.getValue(), rule.getDefaultValue()));
			rules = getMessage(sender, EHungerGameCode.HUNGER_GAME__RULES, String.format("%n%s", ruleJoiner));
		}

		// Step 2: Joining with a new line
		StringJoiner joiner = new StringJoiner("\n");
		joiner.add(name).add(teams).add(borders).add(uhc).add(playerDontReviveTime).add(pvpTime).add(itemOnPlayerKills).add(rules);

		return joiner.toString();
	}

	private String color(String line) {
		int index = line.indexOf(":");
		if (index == -1)
			return line;
		return EColor.WHITE.getInColor(line.substring(0, index), EColor.GOLD) + line.substring(index);
	}
}
