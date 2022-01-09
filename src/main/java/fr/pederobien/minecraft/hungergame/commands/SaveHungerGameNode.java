package fr.pederobien.minecraft.hungergame.commands;

import java.util.function.BiConsumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceSaveNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceSaveNode.PersistenceSaveNodeBuilder;

public class SaveHungerGameNode implements ICodeSender {
	private PersistenceSaveNode saveNode;

	protected SaveHungerGameNode(PersistenceNodeFactory<IHungerGame> factory) {
		// Action when the border serialization fails
		BiConsumer<CommandSender, IHungerGame> onFailToSerialize = (sender, border) -> {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__SAVE_GAME__FAIL_TO_SAVE, border.getName()));
		};
		PersistenceSaveNodeBuilder<IHungerGame> builder = factory.saveNode(onFailToSerialize);

		// Action when the border is serialized
		BiConsumer<CommandSender, IHungerGame> onSerialized = (sender, border) -> {
			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__SAVE_GAME__GAME_SAVED, border.getName());
		};
		builder.onSerialized(onSerialized);

		// Creating the node that save borders
		saveNode = builder.build(EHungerGameCode.HUNGER_GAME__SAVE_GAME__EXPLANATION);

		// Node available if and only if the current border is not null
		saveNode.setAvailable(() -> factory.getPersistence().get() != null);
	}

	/**
	 * @return The node that saves the border characteristics.
	 */
	public PersistenceSaveNode getNode() {
		return saveNode;
	}
}
