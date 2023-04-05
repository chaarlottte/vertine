package fr.wykt.vertine.eventbus;

import fr.wykt.vertine.eventbus.interfaces.IHandler;
import fr.wykt.vertine.eventbus.interfaces.Listener;
import fr.wykt.vertine.eventbus.interfaces.SubscribeEvent;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBus {
    private final Map<IHandler, Map<Class<? extends Event>, List<Listener<?>>>> listeners = new HashMap<>();

    public <T extends Event> void call(T event) {
        listeners.forEach((handler, map) -> {
            if(!handler.listening()) {
                return;
            }

            map.forEach((eventClass, listeners) -> {
                if(event.getClass() != eventClass) {
                    return;
                }

                listeners.forEach(listener -> {
                    Listener<T> listener1 = (Listener<T>) listener;
                    listener1.call(event);
                });
            });
        });
    }

    public void register(IHandler handler) {
        listeners.putIfAbsent(handler, new HashMap<>());

        try {
            for (Field field : handler.getClass().getDeclaredFields()) {
                // Check if the field is annotated with @SubscribeEvent
                if(!field.isAnnotationPresent(SubscribeEvent.class)) {
                    continue;
                }

                // Check if the field type is Listener
                if(!field.getType().getTypeName().equals(Listener.class.getName())) {
                    continue;
                }

                field.setAccessible(true); // Mind the fact that lunar use
                                           // Java 17, so this won't work
                                           // if the field is not public

                Type eventType = ((ParameterizedType) (field.getGenericType())).getActualTypeArguments()[0];
                Class<? extends Event> eventClass = (Class<? extends Event>) this.getClass().getClassLoader().loadClass(eventType.getTypeName());

                Listener<?> listener = (Listener<?>) field.get(handler);
                listeners.get(handler).putIfAbsent(eventClass, new ArrayList<>());
                listeners.get(handler).get(eventClass).add(listener);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
