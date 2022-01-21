package fr.pederobien.minecraft.hungergame.commands;

import fr.pederobien.minecraft.border.commands.borders.BordersCommandTree;
import fr.pederobien.minecraft.commandtree.impl.MinecraftCodeRootNode;
import fr.pederobien.minecraft.commandtree.interfaces.IMinecraftCodeRootNode;
import fr.pederobien.minecraft.game.commands.features.FeaturesCommandTree;
import fr.pederobien.minecraft.game.commands.teams.TeamsCommandTree;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.platform.commands.PlatformNodeFactory;
import fr.pederobien.minecraft.platform.commands.PvpTimeNode;
import fr.pederobien.minecraft.platform.commands.common.AsCurrentNode;
import fr.pederobien.minecraft.platform.commands.persistence.PersistenceNodeFactory;
import fr.pederobien.minecraft.platform.interfaces.IPlatformPersistence;
import fr.pederobien.minecraft.rules.commands.RulesCommandTree;

public class HungerGameCommandTree {
	private IMinecraftCodeRootNode root;
	private PersistenceNodeFactory<IHungerGame> factory;
	private BordersCommandTree bordersNode;
	private RulesCommandTree rulesNode;
	private TeamsCommandTree teamsNode;
	private FeaturesCommandTree featuresNode;
	private AsCurrentNode asCurrentNode;
	private PvpTimeNode pvpTimeNode;
	private NewHungerGameNode newNode;
	private DetailsHungerGameNode detailsNode;
	private RenameHungerGameNode renameNode;
	private DeleteHungerGameNode deleteNode;
	private LoadHungerGameNode loadNode;
	private ListHungerGameNode listNode;
	private SaveHungerGameNode saveNode;
	private PlayerDontReviveTimeHungerGameNode playerDontReviveTimeNode;
	private UhcHungerGameNode uhcNode;
	private ItemOnPlayerKillsHungerGameNode itemOnPlayerKillsNode;

	public HungerGameCommandTree(IPlatformPersistence<IHungerGame> persistence) {
		root = new MinecraftCodeRootNode("hg", EHungerGameCode.HUNGER_GAME__EXPLANATION, () -> true);

		factory = new PersistenceNodeFactory<IHungerGame>(persistence);

		root.add((bordersNode = new BordersCommandTree(() -> getHungerGame())).getRoot().export());
		root.add((rulesNode = new RulesCommandTree(() -> getHungerGame())).getRoot().export());
		root.add((teamsNode = new TeamsCommandTree(() -> getHungerGame())).getRoot().export());
		root.add((featuresNode = new FeaturesCommandTree(() -> getHungerGame())).getRoot().export());
		root.add(asCurrentNode = PlatformNodeFactory.asCurrentNode(() -> getHungerGame()));
		root.add(pvpTimeNode = PlatformNodeFactory.pvpTimeNode(() -> getHungerGame()));

		root.add((newNode = new NewHungerGameNode(factory)).getNode());
		root.add((detailsNode = new DetailsHungerGameNode(factory, bordersNode.getDetailsNode())).getNode());
		root.add((renameNode = new RenameHungerGameNode(factory)).getNode());
		root.add((deleteNode = new DeleteHungerGameNode(factory)).getNode());
		root.add((loadNode = new LoadHungerGameNode(factory)).getNode());
		root.add((listNode = new ListHungerGameNode(factory)).getNode());
		root.add((saveNode = new SaveHungerGameNode(factory)).getNode());

		root.add(playerDontReviveTimeNode = new PlayerDontReviveTimeHungerGameNode(() -> getHungerGame()));
		root.add(uhcNode = new UhcHungerGameNode(() -> getHungerGame()));
		root.add(itemOnPlayerKillsNode = new ItemOnPlayerKillsHungerGameNode(() -> getHungerGame()));
	}

	/**
	 * @return The root of this command tree.
	 */
	public IMinecraftCodeRootNode getRoot() {
		return root;
	}

	/**
	 * @return The last created hunger game or null of no hunger game has been created.
	 */
	public IHungerGame getHungerGame() {
		return factory.getPersistence().get();
	}

	/**
	 * Set the hunger game manipulated by this tree.
	 * 
	 * @param game The new game.
	 */
	public void setHungerGame(IHungerGame game) {
		factory.getPersistence().set(game);
	}

	/**
	 * @return The node to add/remove borders to the current game.
	 */
	public BordersCommandTree getBordersNode() {
		return bordersNode;
	}

	/**
	 * @return The node to modify the rules associated to the current game.
	 */
	public RulesCommandTree getRulesNode() {
		return rulesNode;
	}

	/**
	 * @return The node to modify the teams of the current game.
	 */
	public TeamsCommandTree getTeamsNode() {
		return teamsNode;
	}

	/**
	 * @return The node to modify features of the current game.
	 */
	public FeaturesCommandTree getFeaturesNode() {
		return featuresNode;
	}

	/**
	 * @return The node to specify the current game should be started by the command "./startgame".
	 */
	public AsCurrentNode getAsCurrentNode() {
		return asCurrentNode;
	}

	/**
	 * @return The node to set the time after which the PVP is enabled while the game is in progress.
	 */
	public PvpTimeNode getPvpTimeNode() {
		return pvpTimeNode;
	}

	/**
	 * @return The node that creates a new hunger game to configure.
	 */
	public NewHungerGameNode getNewNode() {
		return newNode;
	}

	/**
	 * @return The node that displays the details of the current game.
	 */
	public DetailsHungerGameNode getDetailsNode() {
		return detailsNode;
	}

	/**
	 * @return The node that renames the current game.
	 */
	public RenameHungerGameNode getRenameNode() {
		return renameNode;
	}

	/**
	 * @return The node that deletes hunger game files.
	 */
	public DeleteHungerGameNode getDeleteNode() {
		return deleteNode;
	}

	/**
	 * @return The node that loads an hunger game to configure.
	 */
	public LoadHungerGameNode getLoadNode() {
		return loadNode;
	}

	/**
	 * @return The node that displays the list of hunger game files.
	 */
	public ListHungerGameNode getListNode() {
		return listNode;
	}

	/**
	 * @return The node that serialize the current game.
	 */
	public SaveHungerGameNode getSaveNode() {
		return saveNode;
	}

	/**
	 * @return The node to specify the time after which players don't revive.
	 */
	public PlayerDontReviveTimeHungerGameNode getPlayerDontReviveTimeNode() {
		return playerDontReviveTimeNode;
	}

	/**
	 * @return The node to enable or disable the UHC mode.
	 */
	public UhcHungerGameNode getUhcNode() {
		return uhcNode;
	}

	/**
	 * @return The node to specify which item is given to a player when he kills another player.
	 */
	public ItemOnPlayerKillsHungerGameNode getItemOnPlayerKillsNode() {
		return itemOnPlayerKillsNode;
	}
}
