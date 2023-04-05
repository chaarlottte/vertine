package fr.wykt.vertine.management.impl;

import fr.wykt.vertine.Vertine;
import fr.wykt.vertine.feature.module.AbstractModule;
import fr.wykt.vertine.feature.module.enums.ModuleCategory;
import fr.wykt.vertine.feature.module.impl.move.FlightModule;
import fr.wykt.vertine.feature.module.impl.visual.HudModule;
import fr.wykt.vertine.management.Manager;

import java.util.List;
import java.util.stream.Collectors;

public final class ModuleManager extends Manager<AbstractModule> {

    public ModuleManager() {
        registerValues(FlightModule.class);
        registerValues(HudModule.class);

        values.forEach(value -> Vertine.INSTANCE.getEventBus().register(value));
    }

    public List<AbstractModule> getValuesByCategory(ModuleCategory category) {
        return values
                .stream()
                .filter(value -> value.getCategory() == category)
                .collect(Collectors.toList());
    }
}
