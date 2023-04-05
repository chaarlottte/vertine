package fr.wykt.vertine.eventbus;

public interface IHandler {
    default boolean listening() {
        return true;
    }
}
