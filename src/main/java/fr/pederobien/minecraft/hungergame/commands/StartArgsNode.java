package fr.pederobien.minecraft.hungergame.commands;

import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeNode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.managers.WorldManager;

public class StartArgsNode extends MinecraftCodeNode {
	private IHungerGame game;
	private StartSkipNode skipNode;

	/**
	 * Creates a command tree to specify argument before starting a game.
	 */
	public StartArgsNode() {
		super("args", null);
		setAvailable(() -> getGame() != null && getGame().getBorders().getBorder(WorldManager.OVERWORLD).isPresent());
		add(skipNode = new StartSkipNode(() -> getGame()));
	}

	/**
	 * @return The hunger game associated to this command tree.
	 */
	public IHungerGame getGame() {
		return game;
	}

	/**
	 * Set the hunger game associated to this command tree.
	 * 
	 * @param game The new game.
	 */
	public void setGame(IHungerGame game) {
		this.game = game;
	}

	/**
	 * @return The node to skip actions before starting a game.
	 */
	public StartSkipNode getSkipNode() {
		return skipNode;
	}
}
