package fr.pederobien.minecraft.hungergame.commands;

import java.util.function.Consumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceDeleteNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceDeleteNode.PersistenceDeleteNodeBuilder;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;

public class DeleteHungerGameNode implements ICodeSender {
	private PersistenceDeleteNode deleteNode;

	protected DeleteHungerGameNode(PersistenceNodeFactory<IHungerGame> factory) {
		// Action to perform when the hunger game file name is missing.
		Consumer<CommandSender> onNameIsMissing = sender -> {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__DELETE_GAME__NAME_IS_MISSING).build());
		};
		PersistenceDeleteNodeBuilder builder = factory.deleteNode(onNameIsMissing);

		// Action to perform when the hunger game file is deleted.
		builder.onDeleted((sender, name) -> sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__DELETE_GAME__GAME_DELETED, name));

		// Creating node that delete hunger game files.
		deleteNode = builder.build(EHungerGameCode.HUNGER_GAME__DELETE_GAME__EXPLANATION);

		// Always available
		deleteNode.setAvailable(() -> true);
	}

	/**
	 * @return The node that delete hunger game files.
	 */
	public PersistenceDeleteNode getNode() {
		return deleteNode;
	}
}
