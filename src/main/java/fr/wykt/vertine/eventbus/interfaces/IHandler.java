package fr.wykt.vertine.eventbus.interfaces;

public interface IHandler {
    default boolean listening() {
        return true;
    }
}
