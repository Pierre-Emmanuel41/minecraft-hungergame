package fr.pederobien.minecraft.hungergame.commands;

import java.util.function.Consumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNewNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNewNode.PersistenceNewNodeBuilder;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;

public class NewHungerGameNode implements ICodeSender {
	private PersistenceNewNode<IHungerGame> newNode;

	protected NewHungerGameNode(PersistenceNodeFactory<IHungerGame> factory) {
		// Action when the name is missing
		Consumer<CommandSender> onNameIsMissing = sender -> {
			send(eventBuilder(EHungerGameCode.HUNGER_GAME__NEW_GAME__NAME_IS_MISSING).build());
		};
		PersistenceNewNodeBuilder<IHungerGame> builder = factory.newNodeBuilder(onNameIsMissing);

		// Action when the name is already used.
		builder.onNameAlreadyTaken((sender, name) -> send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__NEW_GAME__NAME_ALREADY_USED, name)));

		// Action when the hunger game is created.
		builder.onCreated((sender, game) -> {
			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__NEW_GAME__GAME_CREATED, game.getName());
		});

		// Creating the node that creates hunger games.
		newNode = builder.build(EHungerGameCode.HUNGER_GAME__NEW_GAME__EXPLANATION);

		// Node always available.
		newNode.setAvailable(() -> true);
	}

	/**
	 * @return The node that creates borders.
	 */
	public PersistenceNewNode<IHungerGame> getNode() {
		return newNode;
	}
}
