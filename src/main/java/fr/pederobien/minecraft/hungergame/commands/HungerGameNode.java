package fr.pederobien.minecraft.hungergame.commands;

import java.util.function.Function;
import java.util.function.Supplier;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.dictionary.interfaces.IMinecraftCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;

public class HungerGameNode extends MinecraftCodeNode {
	private Supplier<IHungerGame> game;

	/**
	 * Create an hunger game node defined by a label, which correspond to its name, and an explanation.
	 * 
	 * @param game        The game associated to this node.
	 * @param label       The name of the node.
	 * @param explanation The explanation of the node.
	 * @param isAvailable True if this node is available, false otherwise.
	 */
	protected HungerGameNode(Supplier<IHungerGame> game, String label, IMinecraftCode explanation, Function<IHungerGame, Boolean> isAvailable) {
		super(label, explanation, () -> isAvailable.apply(game.get()));
		this.game = game;
	}

	/**
	 * @return The game associated to this node.
	 */
	public IHungerGame getGame() {
		return game.get();
	}
}
