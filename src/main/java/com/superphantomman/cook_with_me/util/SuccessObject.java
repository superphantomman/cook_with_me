package com.superphantomman.cook_with_me.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/*
 * Unwrap fields from object, ignore fields
 * provided by user or not provided
 * and pack it in SuccessObject
 * */

@Slf4j
public final class SuccessObject {
    private final SortedMap<String, String> success = new TreeMap<>();

    public SuccessObject(Object o, Collection<String> ignore) {
        final Class<?> clazz = o.getClass();
        final Field[] fields = clazz.getDeclaredFields();
        final HashSet<String> ignoredFields = new HashSet<>(ignore);

        Object value;
        for (final Field field : fields) {
            if (ignoredFields.contains(field.getName())) continue;

            field.setAccessible(true);
            try {
                value = field.get(o);
                success.put(field.getName(), value.toString());
            } catch (IllegalAccessException e) {
                log.error("Failed to create SuccessObject: {}", e.getMessage());
            }
        }
    }

    public SuccessObject(Object o) {
        this(o, Set.of());
    }

    public Map<String, String> getSuccessFields() {
        return success;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SuccessObject)) return false;

        final Set<Map.Entry<String, String>> entriesThat = ((SuccessObject) o).success.entrySet();
        final Set<Map.Entry<String, String>> entriesThis = this.success.entrySet();

        return entriesThis.containsAll(entriesThat);
    }

    @Override
    public int hashCode() {
        return success.hashCode();
    }
}
