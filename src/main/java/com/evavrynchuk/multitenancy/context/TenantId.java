package com.evavrynchuk.multitenancy.context;

public class TenantId {

    private static final ThreadLocal<String> ID = new ThreadLocal<>();

    public static String get() {
        return ID.get();
    }

    public static void set(String tenantId) {
        ID.set(tenantId);
    }

    public static void remove() {
        ID.remove();
    }

    private TenantId() {
    }
}
