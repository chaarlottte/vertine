package fr.wykt.vertine.management.impl;

import fr.wykt.vertine.NativeLoader;
import fr.wykt.vertine.hook.AbstractHook;
import fr.wykt.vertine.hook.impl.EntityPlayerSPHook;
import fr.wykt.vertine.hook.impl.GuiIngameHook;
import fr.wykt.vertine.hook.impl.KeyBindingHook;
import fr.wykt.vertine.hook.interfaces.HookInfo;
import fr.wykt.vertine.management.Manager;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.util.HashMap;
import java.util.Map;

public final class HookManager extends Manager<AbstractHook> {
    public HookManager() {
        registerValues(EntityPlayerSPHook.class, GuiIngameHook.class, KeyBindingHook.class);
    }

    private final Map<Class<?>, byte[]> oldHooksBytes = new HashMap<>();

    public void enableHooks() {
        values.forEach(hook -> {
            HookInfo info = hook.getClass().getAnnotation(HookInfo.class);

            if(info == null) {
                throw new RuntimeException(hook.getClass().getName() + " is not annotated with @HookInfo");
            }

            Class<?> classToHook = info.value();

            byte[] classBytes = NativeLoader.GetClassBytes(classToHook);
            oldHooksBytes.put(classToHook, classBytes);

            ClassReader cr = new ClassReader(classBytes);
            ClassNode cn = new ClassNode();
            cr.accept(cn, 0);

            hook.hook(cn);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);

            byte[] hookedClassBytes = cw.toByteArray();
            int err = NativeLoader.RedefineClass(classToHook, hookedClassBytes);
            if(err != 0) {
                throw new RuntimeException("Hook " + hook.getClass().getName() + " returned a jvmti error while redefining class: " + err);
            }
        });
    }

    public void disableHooks() {
        oldHooksBytes.forEach(NativeLoader::RedefineClass);
    }
}
