package fr.pederobien.minecrafthungergame.impl.state;

import java.time.LocalTime;
import java.util.Optional;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.pederobien.minecraftborder.interfaces.IBorderConfiguration;
import fr.pederobien.minecraftgameplateform.interfaces.runtime.timeline.IObsTimeLine;
import fr.pederobien.minecraftgameplateform.utils.Plateform;
import fr.pederobien.minecrafthungergame.EHungerGameMessageCode;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGame;
import fr.pederobien.minecraftmanagers.EColor;
import fr.pederobien.minecraftmanagers.PlayerManager;
import fr.pederobien.minecraftmanagers.ScoreboardManager;
import fr.pederobien.minecraftmanagers.WorldManager;

public class InitialState extends AbstractState {
	private IObsTimeLine teamObjective;

	public InitialState(IHungerGame game) {
		super(game);
		teamObjective = new TeamObjective();
	}

	@Override
	public boolean initiate(CommandSender sender, Command command, String label, String[] args) {
		Optional<IBorderConfiguration> optOverworldBorder = getConfiguration().getBorder(WorldManager.OVERWORLD);
		if (!optOverworldBorder.isPresent()) {
			sendNotSynchro(sender, EHungerGameMessageCode.OVERWORLD_BORDER_IS_MISSING, EColor.DARK_RED, getConfiguration().getName());
			return false;
		}
		getConfiguration().getBorders().forEach(border -> border.apply(Plateform.getTimeLine()));
		Plateform.getTimeLine().addObserver(getConfiguration().getPlayerDontReviveTime(), getGame());
		Plateform.getTimeLine().addObserver(getConfiguration().getBorder(WorldManager.OVERWORLD).get().getStartTime(), teamObjective);
		PlayerManager.getPlayers().forEach(player -> getGame().createObjective(ScoreboardManager.createScoreboard(), player));
		return true;
	}

	@Override
	public void start() {
		getGame().setCurrentState(getGame().getStartState()).start();
	}

	private class TeamObjective implements IObsTimeLine {

		@Override
		public int getCountDown() {
			return 0;
		}

		@Override
		public int getCurrentCountDown() {
			return 0;
		}

		@Override
		public void onTime(LocalTime time) {
			getGame().getObjectives().forEach(obj -> obj.addTeams());
		}

		@Override
		public void onCountDownTime(LocalTime currentTime) {

		}

		@Override
		public LocalTime getNextNotifiedTime() {
			return LocalTime.of(0, 0, 0);
		}
	}
}
