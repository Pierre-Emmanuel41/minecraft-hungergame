package fr.pederobien.minecrafthungergame.impl;

import java.util.function.Function;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;

import fr.pederobien.minecraftgameplateform.entries.auto.LocationAutoUpdater;
import fr.pederobien.minecraftgameplateform.entries.auto.OrientationAutoUpdater;
import fr.pederobien.minecraftgameplateform.entries.auto.TimeTaskAutoUpdater;
import fr.pederobien.minecraftgameplateform.entries.auto.WorldBorderSizeUpdater;
import fr.pederobien.minecraftgameplateform.entries.simple.CenterEntry;
import fr.pederobien.minecraftgameplateform.entries.simple.GameTimeEntry;
import fr.pederobien.minecraftgameplateform.entries.simple.LocationEntry;
import fr.pederobien.minecraftgameplateform.entries.simple.PauseTimeEntry;
import fr.pederobien.minecraftgameplateform.entries.simple.TotalTimeEntry;
import fr.pederobien.minecraftgameplateform.impl.element.GameObjective;
import fr.pederobien.minecraftgameplateform.interfaces.element.IBorderConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameConfiguration;
import fr.pederobien.minecrafthungergame.interfaces.IHungerGameObjective;
import fr.pederobien.minecraftmanagers.WorldManager;
import fr.pederobien.minecraftscoreboards.interfaces.IEntry;

public class HungerGameObjective extends GameObjective<IHungerGameConfiguration> implements IHungerGameObjective {
	private int score;

	/**
	 * Create an empty objective based on the given parameters.
	 * 
	 * @param plugin        The plugin used to update this objective.
	 * @param player        The player associated to this objective. This player is used to display its informations.
	 * @param name          The name of this objective.
	 * @param displayName   The name displayed on the given player score board.
	 * @param criteria      The criteria tracked by this objective.
	 * @param displaySlot   The slot where this objective is displayed on player screen.
	 * @param configuration The configuration associated to this objective.
	 */
	public HungerGameObjective(Plugin plugin, Player player, String name, String displayName, String criteria, DisplaySlot displaySlot,
			IHungerGameConfiguration configuration) {
		super(plugin, player, name, displayName, criteria, displaySlot, configuration);
		score = 0;
	}

	/**
	 * Create an empty objective based on the given parameters.
	 * 
	 * @param plugin        The plugin used to update this objective.
	 * @param player        The player associated to this objective. This player is used to display its informations.
	 * @param name          The name of this objective.
	 * @param displayName   The name displayed on the given player score board.
	 * @param displaySlot   The slot where this objective is displayed on player screen.
	 * @param configuration The configuration associated to this objective.
	 */
	public HungerGameObjective(Plugin plugin, Player player, String name, String displayName, DisplaySlot displaySlot, IHungerGameConfiguration configuration) {
		this(plugin, player, name, displayName, "dummy", displaySlot, configuration);
	}

	/**
	 * Create an empty objective based on the given parameters.
	 * 
	 * @param plugin        The plugin used to update this objective.
	 * @param player        The player associated to this objective. This player is used to display its informations.
	 * @param name          The name of this objective.
	 * @param displayName   The name displayed on the given player score board.
	 * @param displaySlot   The slot where this objective is displayed on player screen.
	 * @param configuration The configuration associated to this objective.
	 */
	public HungerGameObjective(Plugin plugin, Player player, String name, String displayName, IHungerGameConfiguration configuration) {
		this(plugin, player, name, displayName, DisplaySlot.SIDEBAR, configuration);
	}

	@Override
	public void initialize() {
		initiate();
		super.initialize();
	}

	@Override
	public void initiate() {
		add(score -> new LocationAutoUpdater(this, new LocationEntry(score)));
		add(score -> new OrientationAutoUpdater(this, new CenterEntry(score, getConfiguration().getBorder(WorldManager.OVERWORLD).get().getBorderCenter())));
		emptyEntry(score--);
		for (IBorderConfiguration border : getConfiguration().getBorders())
			add(score -> new WorldBorderSizeUpdater(this, score, border, "#", true));
		emptyEntry(score--);
		add(score -> new TimeTaskAutoUpdater(this, new TotalTimeEntry(score)));
		add(score -> new TimeTaskAutoUpdater(this, new PauseTimeEntry(score)));
		add(score -> new TimeTaskAutoUpdater(this, new GameTimeEntry(score)));
	}

	private void add(Function<Integer, IEntry> constructor) {
		addEntry(constructor.apply(score));
		score--;
	}
}
