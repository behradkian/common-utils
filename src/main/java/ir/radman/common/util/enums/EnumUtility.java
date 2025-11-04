package ir.radman.common.util.enums;

import ir.radman.common.general.exception.domain.InvalidEnumException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : Pedram Behradkian
 * @date : 2025/11/02
 */
public final class EnumUtility {

    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    private EnumUtility() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Get Enum by ID field (must have getId() method).
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getById(Class<E> enumClass, Object id) {
        if (enumClass == null || id == null) {
            throw new InvalidEnumException(enumClass == null ? "null" : enumClass.getSimpleName(), id);
        }
        String cacheKey = enumClass.getName() + "#id#" + id;
        if (CACHE.containsKey(cacheKey)) {
            return (E) CACHE.get(cacheKey);
        }

        try {
            Method getIdMethod = enumClass.getMethod("getId");
            E result = Arrays.stream(enumClass.getEnumConstants())
                    .filter(e -> {
                        try {
                            Object value = getIdMethod.invoke(e);
                            return id.equals(value);
                        } catch (Exception ex) {
                            return false;
                        }
                    })
                    .findFirst()
                    .orElseThrow(() -> new InvalidEnumException(enumClass.getSimpleName(), id));

            CACHE.put(cacheKey, result);
            return result;
        } catch (NoSuchMethodException e) {
            throw new InvalidEnumException(enumClass.getSimpleName(), "Enum must have getId() method");
        }
    }

    /**
     * Get Enum by CODE field (must have getCode() method).
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getByCode(Class<E> enumClass, String code) {
        if (enumClass == null || code == null)
            throw new InvalidEnumException(enumClass == null ? "null" : enumClass.getSimpleName(), code);

        String cacheKey = enumClass.getName() + "#code#" + code.toUpperCase();
        if (CACHE.containsKey(cacheKey)) {
            return (E) CACHE.get(cacheKey);
        }

        try {
            Method getCodeMethod = enumClass.getMethod("getCode");
            E result = Arrays.stream(enumClass.getEnumConstants())
                    .filter(e -> {
                        try {
                            Object value = getCodeMethod.invoke(e);
                            return code.equalsIgnoreCase(String.valueOf(value));
                        } catch (Exception ex) {
                            return false;
                        }
                    })
                    .findFirst()
                    .orElseThrow(() -> new InvalidEnumException(enumClass.getSimpleName(), code));

            CACHE.put(cacheKey, result);
            return result;
        } catch (NoSuchMethodException e) {
            throw new InvalidEnumException(enumClass.getSimpleName(), "Enum must have getCode() method");
        }
    }

    /**
     * Get Enum by its name (Enum constant name).
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getByName(Class<E> enumClass, String name) {
        if (enumClass == null || name == null)
            throw new InvalidEnumException(enumClass == null ? "null" : enumClass.getSimpleName(), name);

        String cacheKey = enumClass.getName() + "#name#" + name.toUpperCase();
        if (CACHE.containsKey(cacheKey)) {
            return (E) CACHE.get(cacheKey);
        }

        try {
            E result = Enum.valueOf(enumClass, name.toUpperCase());
            CACHE.put(cacheKey, result);
            return result;
        } catch (IllegalArgumentException e) {
            throw new InvalidEnumException(enumClass.getSimpleName(), name);
        }
    }

    /**
     * Clear cache (for testing or refresh scenarios)
     */
    public static void clearCache() {
        CACHE.clear();
    }
}