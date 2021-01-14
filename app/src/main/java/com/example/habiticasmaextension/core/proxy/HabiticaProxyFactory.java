package com.example.habiticasmaextension.core.proxy;

public class HabiticaProxyFactory {
    public static HabiticaProxy createProxy(String userId, String apiToken) { return new HabiticaProxyImpl(userId, apiToken); }
}
