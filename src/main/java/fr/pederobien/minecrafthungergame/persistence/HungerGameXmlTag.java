package fr.pederobien.minecrafthungergame.persistence;

public enum HungerGameXmlTag {
	;

	private String name;

	private HungerGameXmlTag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
