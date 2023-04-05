package fr.wykt.vertine.hook.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface HookInfo {
    Class<?> value();
}
