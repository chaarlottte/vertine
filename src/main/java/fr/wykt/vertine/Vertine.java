package fr.wykt.vertine;

import fr.wykt.vertine.eventbus.EventBus;
import fr.wykt.vertine.eventbus.IHandler;
import fr.wykt.vertine.eventbus.Listener;
import fr.wykt.vertine.eventbus.SubscribeEvent;
import fr.wykt.vertine.eventbus.impl.PlayerKeyEvent;
import fr.wykt.vertine.feature.module.AbstractModule;
import fr.wykt.vertine.management.impl.HookManager;
import fr.wykt.vertine.management.impl.ModuleManager;

public final class Vertine {
    public static final Vertine INSTANCE = new Vertine();

    private final EventBus eventBus = new EventBus();

    private HookManager hookManager;
    private ModuleManager moduleManager;


    public void initialize() {
        // initialize shit
        hookManager = new HookManager();
        moduleManager = new ModuleManager();

        // hook some classes, that will for example add code to call events
        hookManager.enableHooks();

        // register our main event listener (See EventListener class)
        eventBus.register(new EventListener());
    }

    public void selfDestruct() {
        hookManager.disableHooks();
        NativeLoader.UninitializeLoader();
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    private final static class EventListener implements IHandler {
        @SubscribeEvent
        public final Listener<PlayerKeyEvent> playerKeyEventListener = event -> {
            Vertine.INSTANCE.getModuleManager().getValues()
                    .stream()
                    .filter(module -> module.getKey() == event.getKey())
                    .forEach(AbstractModule::toggle);
        };
    }
}
