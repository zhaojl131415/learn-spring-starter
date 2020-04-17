package com.zhao.provider;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhao
 */
public class LocalRegister {

    private static Map<String, Class> LOCALREGISTER = new HashMap<>();

    public static void register(String interfaceName, Class interfaceImpl){
        LOCALREGISTER.put(interfaceName, interfaceImpl);
    }

    public static Class get(String interfaceName){
        return LOCALREGISTER.get(interfaceName);
    }
}
