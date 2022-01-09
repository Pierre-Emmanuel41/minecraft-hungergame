package fr.pederobien.minecraft.hungergame.commands;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceListNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceListNode.PersistenceListNodeBuilder;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;

public class ListHungerGameNode implements ICodeSender {
	private PersistenceListNode listNode;

	protected ListHungerGameNode(PersistenceNodeFactory<IHungerGame> factory) {
		// Action When there is no border file on the server
		Consumer<CommandSender> onNoElement = sender -> sendSuccessful(sender, EHungerGameCode.HUNGER_GAME_LIST_GAME__NO_ELEMENT);
		PersistenceListNodeBuilder builder = factory.listNode(onNoElement);

		// Action when there is one border file on the server
		BiConsumer<CommandSender, String> onOneElement = (sender, border) -> sendSuccessful(sender, EHungerGameCode.HUNGER_GAME_LIST_GAME__ONE_ELEMENT, border);
		builder.onOneElement(onOneElement);

		// Action when there are several border files on the server
		BiConsumer<CommandSender, List<String>> onSeveralElements = (sender, borders) -> {
			StringJoiner joiner = new StringJoiner("\n");
			for (String border : borders)
				joiner.add(border);

			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME_LIST_GAME__SEVERAL_ELEMENTS, joiner);
		};
		builder.onSeveralElements(onSeveralElements);

		// Creating the node that display the list of border files
		listNode = builder.build(EHungerGameCode.HUNGER_GAME_LIST_GAME__EXPLANATION);

		// Always available
		listNode.setAvailable(() -> true);
	}

	/**
	 * @return The node that displays the list of border files.
	 */
	public PersistenceListNode getNode() {
		return listNode;
	}
}
