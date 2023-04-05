package fr.wykt.vertine.hook.impl;

import fr.wykt.vertine.Vertine;
import fr.wykt.vertine.eventbus.impl.PlayerKeyEvent;
import fr.wykt.vertine.hook.AbstractHook;
import fr.wykt.vertine.hook.interfaces.HookInfo;
import fr.wykt.vertine.utils.asm.ASMUtil;
import net.minecraft.client.settings.KeyBinding;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

@HookInfo(KeyBinding.class)
public final class KeyBindingHook extends AbstractHook {
    @Override
    public void hook(ClassNode cn) {
        MethodNode onTickMn = ASMUtil.getMethod(cn, "onTick", "(I)V");

        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ILOAD, 0));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, this.getClass().getName().replace(".", "/"), "onKeyCall", "(I)V"));
        onTickMn.instructions.insertBefore(onTickMn.instructions.get(1), list);
    }

    public static void onKeyCall(int key) {
        Vertine.INSTANCE.getEventBus().call(new PlayerKeyEvent(key));
    }
}
