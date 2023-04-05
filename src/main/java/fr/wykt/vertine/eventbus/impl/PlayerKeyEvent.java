package fr.wykt.vertine.eventbus.impl;

import fr.wykt.vertine.eventbus.Event;

public final class PlayerKeyEvent extends Event {
    private final int key;

    public PlayerKeyEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
