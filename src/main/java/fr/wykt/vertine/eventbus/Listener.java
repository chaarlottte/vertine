package fr.wykt.vertine.eventbus;

public interface Listener<T extends Event> {
    void call(T event);
}
