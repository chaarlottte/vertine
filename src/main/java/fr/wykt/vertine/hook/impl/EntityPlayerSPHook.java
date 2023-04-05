package fr.wykt.vertine.hook.impl;

import fr.wykt.vertine.Vertine;
import fr.wykt.vertine.eventbus.impl.PlayerUpdateEvent;
import fr.wykt.vertine.hook.AbstractHook;
import fr.wykt.vertine.hook.interfaces.HookInfo;
import fr.wykt.vertine.utils.asm.ASMUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

@HookInfo(EntityPlayerSP.class)
public final class EntityPlayerSPHook extends AbstractHook {
    // This will add a method call before super.onUpdate() in EntityPlayerSP#onUpdate
    // Example:
    // EntityPlayerSPHook.onUpdateEventCall();
    // super.onUpdate();
    // ---------------------------------------
    // It would have been better to do the event call directly in the method instead of using a proxy method, but I was lazy, sorry :)
    @Override
    public void hook(ClassNode cn) {
        MethodNode onUpdateMn = ASMUtil.getMethod(cn, "onUpdate", "()V");

        for (AbstractInsnNode instruction : onUpdateMn.instructions) {
            if(!(instruction instanceof MethodInsnNode)) {
                continue;
            }

            MethodInsnNode min = (MethodInsnNode) instruction;

            if(min.name.equals("onUpdate")) {
                onUpdateMn.instructions.insertBefore(min.getPrevious(), new MethodInsnNode(Opcodes.INVOKESTATIC, EntityPlayerSPHook.class.getName().replace(".", "/"), "onUpdateEventCall", "()V", false));
            }
        }
    }

    public static void onUpdateEventCall() {
        Vertine.INSTANCE.getEventBus().call(new PlayerUpdateEvent());
    }
}
