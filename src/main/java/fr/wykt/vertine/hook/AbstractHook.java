package fr.wykt.vertine.hook;

import org.objectweb.asm.tree.ClassNode;

public abstract class AbstractHook {
    public abstract void hook(ClassNode cn);
}
