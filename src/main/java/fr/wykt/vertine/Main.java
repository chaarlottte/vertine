package fr.wykt.vertine;

public final class Main {
    public static void main() {
        try {
            Vertine.INSTANCE.initialize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
