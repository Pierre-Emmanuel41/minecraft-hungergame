package fr.pederobien.minecraft.hungergame.commands;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.commandtree.interfaces.ICodeSender;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceLoadNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceLoadNode.PersistenceLoadNodeBuilder;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;

public class LoadHungerGameNode implements ICodeSender {
	private PersistenceLoadNode loadNode;

	protected LoadHungerGameNode(PersistenceNodeFactory<IHungerGame> factory) {
		// Action to perform when the border file name to delete is missing.
		Consumer<CommandSender> onNameIsMissing = sender -> {
			send(eventBuilder(sender, EHungerGameCode.HUNGER_GAME__LOAD_GAME__NAME_IS_MISSING).build());
		};
		PersistenceLoadNodeBuilder<IHungerGame> builder = factory.loadNode(onNameIsMissing);

		// Action to perform when a border is loaded.
		BiConsumer<CommandSender, IHungerGame> onLoaded = (sender, border) -> {
			sendSuccessful(sender, EHungerGameCode.HUNGER_GAME__LOAD_GAME__GAME_LOADED, border.getName());
		};
		builder.onLoaded(onLoaded);

		// Creating the node that loads border.
		loadNode = builder.build(EHungerGameCode.HUNGER_GAME__LOAD_GAME__EXPLANATION);

		// Always available
		loadNode.setAvailable(() -> true);
	}

	/**
	 * @return The persistence that load borders
	 */
	public PersistenceLoadNode getNode() {
		return loadNode;
	}
}
