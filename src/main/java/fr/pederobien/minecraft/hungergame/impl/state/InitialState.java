package fr.pederobien.minecraft.hungergame.impl.state;

import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraft.hungergame.EHungerGameMessageCode;
import fr.pederobien.minecraft.hungergame.interfaces.IHungerGame;
import fr.pederobien.minecraftborder.interfaces.IBorderConfiguration;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.minecraftmanagers.WorldManager;
import fr.pederobien.minecraftrules.impl.GameRule;

public class InitialState extends AbstractState {

	public InitialState(IHungerGame game) {
		super(game);
	}

	@Override
	public boolean initiate(CommandSender sender, Command command, String label, String[] args) {
		Optional<IBorderConfiguration> optOverworldBorder = getConfiguration().getBorder(WorldManager.OVERWORLD);
		if (!optOverworldBorder.isPresent()) {
			sendNotSynchro(sender, EHungerGameMessageCode.HG_OVERWORLD_BORDER_IS_MISSING, EColor.DARK_RED, getConfiguration().getName());
			return false;
		}

		getConfiguration().getBorders().forEach(border -> border.apply(Plateform.getTimeLine()));
		Plateform.getTimeLine().addObserver(getConfiguration().getPlayerDontReviveTime(), getGame());

		if (getConfiguration().isUhc())
			GameRule.NATURAL_REGENERATION.setValue(false);

		return true;
	}

	@Override
	public void start() {
		getGame().setCurrentState(getGame().getStartState()).start();
	}
}
