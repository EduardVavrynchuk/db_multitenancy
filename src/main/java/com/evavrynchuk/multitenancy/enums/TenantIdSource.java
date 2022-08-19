package com.evavrynchuk.multitenancy.enums;

public enum TenantIdSource {
    MY_DB_1("multitenancy1"),
    MY_DB_2("multitenancy2");

    private final String dataSource;

    TenantIdSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public static boolean contains(String tenantId) {

        for (TenantIdSource c : TenantIdSource.values()) {
            if (c.name().equals(tenantId)) {
                return true;
            }
        }

        return false;
    }

    public String getDataSource() {
        return dataSource;
    }
}
