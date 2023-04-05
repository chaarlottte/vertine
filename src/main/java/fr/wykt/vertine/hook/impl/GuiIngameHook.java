package fr.wykt.vertine.hook.impl;

import fr.wykt.vertine.Vertine;
import fr.wykt.vertine.eventbus.impl.Render2DEvent;
import fr.wykt.vertine.hook.AbstractHook;
import fr.wykt.vertine.hook.interfaces.HookInfo;
import fr.wykt.vertine.utils.asm.ASMUtil;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

@HookInfo(GuiIngame.class)
public class GuiIngameHook extends AbstractHook {
    @Override
    public void hook(ClassNode cn) {
        MethodNode renderGameOverlayMn = ASMUtil.getMethod(cn, "renderGameOverlay", "(F)V");

        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 2)); // load scaledresolution into stack
        list.add(new VarInsnNode(Opcodes.FLOAD, 1)); // load partialticks into stack
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, GuiIngameHook.class.getName().replace(".", "/"), "renderGameOverlayCall", "(L" + ScaledResolution.class.getName().replace(".", "/") + ";F)V", false));
        renderGameOverlayMn.instructions.insertBefore(renderGameOverlayMn.instructions.getLast().getPrevious(), list);
    }

    public static void renderGameOverlayCall(ScaledResolution scaledResolution, float partialTicks) {
        Vertine.INSTANCE.getEventBus().call(new Render2DEvent(scaledResolution, partialTicks));
    }
}
