package fr.pederobien.minecraft.hungergame.commands;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.WorldManager;

public class StopArgsNode extends MinecraftCodeNode {
	private IHungerGame game;
	private StopSkipNode skipNode;

	/**
	 * Creates a command tree to specify argument before starting a game.
	 */
	public StopArgsNode() {
		super("args", null);
		setAvailable(() -> getGame() != null && getGame().getBorders().getBorder(WorldManager.OVERWORLD).isPresent());
		add(skipNode = new StopSkipNode(() -> getGame()));
	}

	/**
	 * @return The hunger game associated to this node.
	 */
	public IHungerGame getGame() {
		return game;
	}

	/**
	 * Set the hunger game associated to this node.
	 * 
	 * @param game The new game.
	 */
	public void setGame(IHungerGame game) {
		this.game = game;
	}

	/**
	 * @return The node to skip actions before stopping a game.
	 */
	public StopSkipNode getSkipNode() {
		return skipNode;
	}
}
