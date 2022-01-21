# Presentation

An hunger game is defined by a list of teams, a list of features, a list of borders. The borders are defined by an initial diameter, a final diameter and a speed. For more details about the configuration of a border, please have a look [there](https://github.com/Pierre-Emmanuel41/minecraft-border/blob/1.0_MC_1.13.2-SNAPSHOT/README.md). The course of a game is divided into 2 stages :

* The first one correspond to the time during which players mine in order to get stuff.
* The second one correspond to the time during which borders shrink and when players fight for the win.

# Commands

From the <code>HGPlugin</code> class, the developer can have access to the <code>HungerGameCommandTree</code>. This tree gather commands in order to modify borders, teams, rules for an hunger game. The root is "hg" which means that when registered as commands arguments for a game, the user has access to the following tree:  

<code>hg</code></code> - To configure an hungergame.</br>
&ensp;<code>ascurrent</code> - To set the game to start with the command "./startgame".</br>
&ensp;<code>borders</code> - To add/remove borders.</br>
&ensp;&ensp;<code>add</code> - To add borders to a game.</br>
&ensp;&ensp;<code>details</code> - To display characteristics of one or several borders for a game.</br>
&ensp;&ensp;<code>list</code> - To display the name of border associated to a world.</br>
&ensp;&ensp;<code>reload</code> - To reload a border file.</br>
&ensp;&ensp;<code>remove</code> - To remove borders from a game.</br>
&ensp;<code>delete</code> - To delete the file of an hunger game.</br>
&ensp;<code>details</code> - To display the characteristics of the hunger game.</br>
&ensp;<code>features</code> - To enable/disable and setup features.</br>
&ensp;&ensp;<code>enable</code> - To enable several features.</br>
&ensp;&ensp;<code>args</code> - To set the feature argument before starting it.</br>
&ensp;&ensp;<code>disable</code> - To disable several features.</br>
&ensp;<code>itemOnPlayerKill</code> - Too set the item to give to a player that killed another player.</br>
&ensp;<code>list</code> - To display all hunger games.</br>
&ensp;<code>load</code> - To load an hunger game.</br>
&ensp;<code>new</code> - To create a new hunger game to configure.</br>
&ensp;<code>playerDontReviveTime</code> - To set the time after which players respawn in spectator mode.</br>
&ensp;<code>pvpTime</code> - To set the time after which the PVP is enabled.</br>
&ensp;<code>rename</code> - To rename the hunger game.</br>
&ensp;<code>rules</code> - To modify rules characteristics.</br>
&ensp;&ensp;<code>announceAdvancements</code> - To enable or disable the advancements announcement.</br>
&ensp;&ensp;<code>displayTeamMatesLocation</code> - To enable or disable the display of player's team mates location.</br>
&ensp;&ensp;<code>maxProtectionOnDiamonds</code> - To set the max level of the protection enchantment on diamonds armor pieces.</br>
&ensp;&ensp;<code>naturalRegeneration</code> - To set if players can regenerate health naturally through their hunger bar.</br>
&ensp;&ensp;<code>pvp</code> - To modify the PVP game rule value.</br>
&ensp;&ensp;<code>revivePlayerNearTeamMate</code> - To set whether or not a respawning player should be teleported to a team mate.</br>
&ensp;<code>save</code> - To save the characteristics of an hunger game.</br>
&ensp;<code>teams</code> - To modify a list of teams of a game.</br>
&ensp;&ensp;<code>add</code> - To add a team to a game or to add a player to a team.</br>
&ensp;&ensp;&ensp;<code>player</code> - To add players to a team.</br>
&ensp;&ensp;&ensp;<code>team</code> - To add a team to a game.</br>
&ensp;&ensp;<code>list</code> - To display the list of team.</br>
&ensp;&ensp;<code>modify</code> - To modify the characteristics of a team.</br>
&ensp;&ensp;&ensp;<code>color</code> - To modify the color of a team.</br>
&ensp;&ensp;&ensp;<code>name</code> - To modify the name of a team.</br>
&ensp;&ensp;<code>move</code> - To move a player from a team to another one.</br>
&ensp;&ensp;<code>random</code> - To create random teams.</br>
&ensp;&ensp;<code>remove</code> - To remove a team from a game or players from a team.</br>
&ensp;&ensp;&ensp;<code>player</code> - To remove players from a team.</br>
&ensp;&ensp;&ensp;<code>team</code> - To remove a team from a game.</br>
&ensp;<code>UHC</code> - To enable or disable the UHC mode.</br>

# While the game is in progress

The game can start if and only if a border is registered for the overworld.

At the beginning of the game, the following actions are performed for each player registered in teams:  
- The <code>resistance</code>, <code>regeneration</code> and <code>saturation</code> effects are given for 30s.  
- The food level is reset to its maximum value (20).  
- The max health is reset to its max value (20).  
- The health is set to 20.  
- The inventory is cleaned.  
- The experience level is set to 0.  
- The game mode is set to <code>SURVIVAL</code>.
- The scoreboard is updated. It contains :  
&ensp; - The player coordinates relative to the overworld border center.</br>
&ensp; - The direction to follow in order to reach the overworld border center.</br>
&ensp; - For each border registered in the game, The time remaining before the borders move and their current diameter.</br>

Then each teams are teleported randomly in the overworld according to the overworld border parameters (center and initial diameter).  
Finally, the time in the overworld is set to 0 (equivalent to executing the command "./time set day"), The game rule <code>DO_DAY_LIGHT_CYCLE</code> is set to true and the game rule <code>DO_FIRE_TICK</code> is set to false.

It may be possible to specify some arguments while starting an hunger game :

<code>skip</code> - To skip some actions before starting a game.</br>
&ensp;<code>clearInventory</code> - In order not to modify the inventory of players registered in teams.</br>
&ensp;<code>createServerTeam</code> - In order not to not create teams on the server.</br>
&ensp;<code>doDayLightCycle</code> - In order not to set the game rule <code>DO_DAY_LIGHT_CYCLE</code> to true.</br>
&ensp;<code>doFireTick</code> - In order to not set the game rule <code>DO_FIRE_TICK</code> to false.</br>
&ensp;<code>expLevel</code> - In order to not modify the experience level of players registered in teams.</br>
&ensp;<code>foodLevel</code> - In order to not modify the food level of players registered in teams.</br>
&ensp;<code>gameMode</code> - In order to not modify the game mode of players registered in teams.</br>
&ensp;<code>giveEffects</code> - In order to not give resistance, regeneration and saturation to players registered in teams.</br>
&ensp;<code>health</code> - In order to not modify the health of players registered in teams.</br>
&ensp;<code>maxHealth</code> - In order to not modify the max health of players registered in teams.</br>
&ensp;<code>setDay</code> - In order to not modify the time in the overworld.</br>
&ensp;<code>teleport</code> - In order to not teleport randomly teams in the overworld.</br>
&ensp;<code>weatherClear</code> - In order to not clear the weather.</br>

When the game is paused each player in survival mode will be in <code>SPECTATOR</code> mode and are not able to move. The players that are already dead are not impacted.
When the game is resumed, the game mode of not dead players is <code>SURVIVAL</code>.

When a game is stopped, the following actions are performed:
- Borders are reset
- Teams are removed from the server
- The game mode of player is updated to be <code>CREATIVE</code>

In the same way, it is possible to specify arguments while stopping an hunger game :

<code>skip</code> - To skip some actions before stopping a game.</br>
&ensp;<code>deleteServerTeam</code> - In order to not delete the teams on the server.</br>
&ensp;<code>gameMode</code> - In order to not modify the game of players registered in teams.</br>
&ensp;<code>resetBorders</code> - In order to not reset the registered borders.</br>

# Features

If the plugin [minecraft-chat](https://github.com/Pierre-Emmanuel41/minecraft-chat.git) is present on the server, then when the game is started a chat associated to each teams is created. They are removed when the game is stopped.