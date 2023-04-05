package fr.wykt.vertine.utils.asm;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public final class ASMUtil {
    // Find a MethodNode that match with @name and @desc in cn.methods
    public static MethodNode getMethod(ClassNode cn, String name, String desc) {
        return cn.methods
                .stream()
                .filter(mn -> mn.name.equals(name) && mn.desc.equals(desc))
                .findFirst()
                .orElse(null);
    }
}
