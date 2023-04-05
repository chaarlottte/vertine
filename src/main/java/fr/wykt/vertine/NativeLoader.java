package fr.wykt.vertine;

public final class NativeLoader {
    // Change class bytecode at runtime
    public static native int RedefineClass(Class<?> clazz, byte[] bytes);

    // Get class bytes with Class parameter
    public static native byte[] GetClassBytes(Class<?> clazz);

    // Unload loader which should be useless after self destruct
    public static native void UninitializeLoader();
}
