package fr.wykt.vertine.feature.module;

import fr.wykt.vertine.eventbus.interfaces.IHandler;
import fr.wykt.vertine.feature.module.enums.ModuleCategory;
import fr.wykt.vertine.feature.module.interfaces.ModuleInfo;
import fr.wykt.vertine.utils.IMinecraft;

public abstract class AbstractModule implements IMinecraft, IHandler {
    private final String name, description;
    private final ModuleCategory category;

    private int key;
    private boolean enabled;

    public AbstractModule() {
        ModuleInfo info = this.getClass().getAnnotation(ModuleInfo.class);

        if(info == null) {
            throw new RuntimeException(this.getClass().getName() + " is not annotated with @ModuleInfo");
        }

        this.name = info.name();
        this.description = info.description();
        this.key = info.key();
        this.category = info.category();
        this.enabled = info.enabled();
    }

    public void toggle() {
        setState(!enabled);
    }

    public void setState(boolean enabled) {
        // added this check, so it doesn't call onEnable when module is already enabled and vice versa
        if(this.enabled == enabled) {
            return;
        }

        this.enabled = enabled;

        if(enabled)
            onEnable();
        else
            onDisable();
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected void onEnable() {}
    protected void onDisable() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public boolean listening() {
        return enabled;
    }
}
