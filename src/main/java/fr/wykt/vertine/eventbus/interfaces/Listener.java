package fr.wykt.vertine.eventbus.interfaces;

import fr.wykt.vertine.eventbus.Event;

public interface Listener<T extends Event> {
    void call(T event);
}
