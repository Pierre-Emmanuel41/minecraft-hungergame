package fr.pederobien.minecraft.hungergame.persistence;

public enum HungerGameXmlTag {
	NAME("name"), BORDERS("borders"), BORDER("border"), TIMES("times"), PVP("pvp"), PLAYER_DONT_REVIVE("playerDontRevive"), TEAMS("teams"), TEAM("team"), COLOR("color"),
	IS_UHC("isUhc"), ITEM_ON_PLAYER_KILLS("itemOnPlayerKills"), RULES("rules"), RULE("rule"), CURRENT("current");

	private String name;

	private HungerGameXmlTag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
