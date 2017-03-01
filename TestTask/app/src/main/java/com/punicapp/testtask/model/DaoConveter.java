
package com.punicapp.testtask.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DaoConveter {

    public static final String GET_PREFFIX = "get";
    public static final String SET_PREFFIX = "set";
    public static final String SET_BOOL_PREFFIX = "is";


    public static <T extends IDao, U extends IDao> List<T> convert(List<U> src, Class<T> dest) {
        List<T> result = new ArrayList<>();
        for (U tmp : src) {
            T res = convert(tmp, dest);
            result.add(res);
        }
        return result;
    }

    public static <T extends IDao, U extends IDao> T convert(U src, Class<T> dest) {
        try {
            Class<?>[] destIFaces = dest.getInterfaces();
            Class<?>[] srcInterfaces = src.getClass().getInterfaces();
            Class<?> aClass = checkFirst(destIFaces, srcInterfaces);
            if (aClass == null) {
                throw new IllegalStateException("Hierarhies are different!");
            }

            T result;
            result = dest.newInstance();
            IDAOInfo info = filterDao(aClass);
            Map<String, Method> getters = info.getGetters();
            Map<String, Method> setters = info.getSetters();
            for (String key : getters.keySet()) {
                if (!setters.containsKey(key)) {
                    continue;
                }
                Method setter = setters.get(key);
                Method getter = getters.get(key);
                setter.setAccessible(true);
                getter.setAccessible(true);
                Object getValue = getter.invoke(src);
                setter.invoke(result, getValue);
            }
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Class<?> checkFirst(Class<?>[] destIFaces, Class<?>[] srcInterfaces) {
        List<Class<?>> srcSearch = Arrays.asList(srcInterfaces);
        for (Class dest : destIFaces) {
            if (srcSearch.contains(dest)) {
                return dest;
            }
        }
        return null;
    }

    private static IDAOInfo filterDao(Class<?> toInit) {
        Map<String, Method> getters = new HashMap<>();
        Map<String, Method> setters = new HashMap<>();
        Method[] declaredMethods = toInit.getDeclaredMethods();
        for (Method meth : declaredMethods) {
            String name = meth.getName();
            String propName = tryToFormatName(name);
            if (isGetter(name) && propName != null) {
                getters.put(propName, meth);
            } else if (isSetterName(name) && propName != null) {
                setters.put(propName, meth);
            }
        }
        return new IDAOInfo(getters, setters);
    }

    private static String tryToFormatName(String name) {
        if (name.startsWith(GET_PREFFIX) && name.length() > 3) {
            String subName = name.substring(3, name.length());
            return subName;
        }
        if (name.startsWith(SET_PREFFIX) && name.length() > 3) {
            String subName = name.substring(3, name.length());
            return subName;
        }
        if (name.startsWith(SET_BOOL_PREFFIX) && name.length() > 2) {
            String subName = name.substring(2, name.length());
            return subName;
        }
        return null;
    }

    private static boolean isSetterName(String name) {
        return name != null && (name.startsWith(SET_BOOL_PREFFIX) || name.startsWith(SET_PREFFIX));
    }

    private static boolean isGetter(String name) {
        return name != null && (name.startsWith(GET_PREFFIX));
    }

    static class IDAOInfo {
        private Map<String, Method> getters;
        private Map<String, Method> setters;

        public IDAOInfo(Map<String, Method> getters, Map<String, Method> setters) {
            this.getters = getters;
            this.setters = setters;
        }

        public Map<String, Method> getGetters() {
            return getters;
        }

        public Map<String, Method> getSetters() {
            return setters;
        }
    }
}
