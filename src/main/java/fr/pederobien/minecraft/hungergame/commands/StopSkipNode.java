package fr.pederobien.minecraft.hungergame.commands;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.google.common.base.Predicate;

import fr.pederobien.minecraft.game.event.GameStopPostEvent;
import fr.pederobien.minecraft.hungergame.impl.EHungerGameCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraft.hungergame.interfaces.IStopActionList.StopAction;
import fr.pederobien.utils.event.EventHandler;
import fr.pederobien.utils.event.EventManager;
import fr.pederobien.utils.event.EventPriority;
import fr.pederobien.utils.event.IEventListener;

public class StopSkipNode extends HungerGameNode implements IEventListener {

	/**
	 * Creates a node to skip actions before stopping a game.
	 * 
	 * @param game The game associated to this node.
	 */
	protected StopSkipNode(Supplier<IHungerGame> game) {
		super(game, "skip", EHungerGameCode.HUNGER_GAME__SKIP__EXPLANATION, g -> g != null);
		EventManager.registerListener(this);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		switch (args.length) {
		case 0:
			return emptyList();
		default:
			List<StopAction> actionList = asList(StopAction.values());
			List<String> alreadyMentionned = asList(args);
			Predicate<StopAction> filter = action -> !alreadyMentionned.contains(action.getName());

			return filter(actionList.stream().filter(filter).map(action -> action.getName()), args);
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (String name : args) {
			Optional<StopAction> optAction = StopAction.getByName(name);
			if (optAction.isPresent()) {
				getGame().getStopActionList().set(optAction.get(), false);
			}
		}
		return true;
	}

	@EventHandler(priority = EventPriority.HIGH)
	private void onGameStop(GameStopPostEvent event) {
		if (!event.getGame().equals(getGame()))
			return;

		getGame().getStopActionList().reset();
	}
}
