package fr.wykt.vertine.feature.module.enums;

public enum ModuleCategory {
    COMBAT("Combat"),
    EXPLOIT("Exploit"),
    MOVE("Move"),
    PLAYER("Player"),
    VISUAL("Visual"),
    WORLD("World");

    private final String name;

    ModuleCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
