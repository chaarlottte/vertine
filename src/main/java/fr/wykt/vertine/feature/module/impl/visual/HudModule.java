package fr.wykt.vertine.feature.module.impl.visual;

import fr.wykt.vertine.Vertine;
import fr.wykt.vertine.eventbus.Listener;
import fr.wykt.vertine.eventbus.SubscribeEvent;
import fr.wykt.vertine.eventbus.impl.Render2DEvent;
import fr.wykt.vertine.feature.module.AbstractModule;
import fr.wykt.vertine.feature.module.enums.ModuleCategory;
import fr.wykt.vertine.feature.module.interfaces.ModuleInfo;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ModuleInfo(name = "HUD", category = ModuleCategory.VISUAL, enabled = true)
public final class HudModule extends AbstractModule {
    @SubscribeEvent
    public final Listener<Render2DEvent> render2DEventListener = event -> {
        mc.fontRendererObj.drawStringWithShadow("vertine", 3, 3, -1);

        List<AbstractModule> enabledModules = Vertine.INSTANCE.getModuleManager().getValues()
                .stream()
                .filter(AbstractModule::isEnabled)
                .sorted(Comparator.comparingInt(module -> -mc.fontRendererObj.getStringWidth(module.getName())))
                .collect(Collectors.toList());

        AtomicInteger index = new AtomicInteger();

        enabledModules.forEach(module -> {
            mc.fontRendererObj.drawStringWithShadow(module.getName(), event.getScaledResolution().getScaledWidth() - mc.fontRendererObj.getStringWidth(module.getName()) - 3, 3 + index.get() * mc.fontRendererObj.FONT_HEIGHT, -1);
            index.incrementAndGet();
        });
    };
}
