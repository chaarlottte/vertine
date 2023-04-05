package fr.wykt.vertine.feature.module.interfaces;

import fr.wykt.vertine.feature.module.enums.ModuleCategory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
    String name();
    String description() default "Default module description";
    ModuleCategory category();
    int key() default -1;
    boolean enabled() default false;
}
