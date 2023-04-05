package fr.wykt.vertine.feature.module.impl.move;

import fr.wykt.vertine.eventbus.interfaces.Listener;
import fr.wykt.vertine.eventbus.interfaces.SubscribeEvent;
import fr.wykt.vertine.eventbus.impl.PlayerUpdateEvent;
import fr.wykt.vertine.feature.module.AbstractModule;
import fr.wykt.vertine.feature.module.enums.ModuleCategory;
import fr.wykt.vertine.feature.module.interfaces.ModuleInfo;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Flight", description = "Fly like a bird", category = ModuleCategory.MOVE, key = Keyboard.KEY_F)
public final class FlightModule extends AbstractModule {
    @SubscribeEvent
    public final Listener<PlayerUpdateEvent> playerUpdateEventListener = event -> {
        mc.thePlayer.motionY = 0;
    };
}
