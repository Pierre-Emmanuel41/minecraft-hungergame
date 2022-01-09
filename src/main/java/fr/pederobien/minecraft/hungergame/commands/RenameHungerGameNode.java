package fr.pederobien.minecraft.hungergame.commands;

import java.util.function.BiConsumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceRenameNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceRenameNode.PersistenceRenameNodeBuilder;
import fr.pederobien.utils.consumers.Consumer3;

public class RenameHungerGameNode implements ICodeSender {
	private PersistenceRenameNode renameNode;

	protected RenameHungerGameNode(PersistenceNodeFactory<IHungerGame> factory) {
		// Action when the new border name is missing.
		BiConsumer<CommandSender, String> onNameIsMissing = (sender, name) -> {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__RENAME_GAME__NAME_IS_MISSING, name));
		};
		PersistenceRenameNodeBuilder builder = factory.renameNodebuilder(onNameIsMissing);

		// Action when the new border name is already used.
		Consumer3<CommandSender, String, String> onNameAlreadyTaken = (sender, oldName, newName) -> {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__RENAME_GAME__NAME_ALREADY_USED, oldName, newName));
		};
		builder.onNameAlreadyTaken(onNameAlreadyTaken);

		// Action when the border has been renamed.
		Consumer3<CommandSender, String, String> onRenamed = (sender, oldName, newName) -> {
			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__RENAME_GAME__GAME_RENAMED, oldName, newName);
		};
		builder.onRenamed(onRenamed);

		// Creating the node that rename borders
		renameNode = builder.build(EHungerGameCode.HUNGER_GAME__RENAME_GAME__EXPLANATION);

		// Node available if and only if the current border is not null
		renameNode.setAvailable(() -> factory.getPersistence().get() != null);
	}

	/**
	 * @return The node that renames borders.
	 */
	public PersistenceRenameNode getNode() {
		return renameNode;
	}
}
