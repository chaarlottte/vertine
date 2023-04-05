package fr.wykt.vertine.management;

import java.util.ArrayList;
import java.util.List;

public abstract class Manager<T> {
    protected final List<T> values = new ArrayList<>();

    public List<T> getValues() {
        return values;
    }

    protected void registerValues(Class<? extends T>... classes) {
        for (Class<? extends T> aClass : classes) {
            try {
                values.add(aClass.getDeclaredConstructor().newInstance());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public T getValue(Class<?> clazz) {
        return values
                .stream()
                .filter(o -> o.getClass() == clazz)
                .findFirst()
                .orElse(null);
    }
}
