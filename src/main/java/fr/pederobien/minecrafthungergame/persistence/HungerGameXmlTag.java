package fr.pederobien.minecrafthungergame.persistence;

public enum HungerGameXmlTag {
	NAME("name"), BORDERS("borders"), BORDER("border"), TIMES("times"), PVP("pvp"), PLAYER_DONT_REVIVE("playerDontRevive"), TEAMS("teams"), TEAM("team"), COLOR("color"),
	IS_UHC("isUhc");

	private String name;

	private HungerGameXmlTag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
