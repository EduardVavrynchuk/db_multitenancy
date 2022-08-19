package com.evavrynchuk.multitenancy.hibernate;

import com.evavrynchuk.multitenancy.context.TenantId;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class DatabaseTenantResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        return TenantId.get();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }

}