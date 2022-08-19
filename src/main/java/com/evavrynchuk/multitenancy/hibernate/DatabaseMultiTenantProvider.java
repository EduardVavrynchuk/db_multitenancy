package com.evavrynchuk.multitenancy.hibernate;

import com.evavrynchuk.multitenancy.enums.TenantIdSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.engine.jndi.spi.JndiService;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Startable;
import org.hibernate.service.spi.Stoppable;

public class DatabaseMultiTenantProvider extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl implements
        ServiceRegistryAwareService, Startable, Stoppable {

    private JndiService jndiService;
    private final Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    private final static String DATA_SOURCE_LOOKUP = "java:/jboss/datasources/%s";
    private final static String ERROR_DATA_SOURCE_LOOKUP = "Error trying to get datasource ['" + DATA_SOURCE_LOOKUP + "']";

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        jndiService = serviceRegistry.getService(JndiService.class);
        if (jndiService == null) {
            throw new HibernateException("Could not locate JndiService from DatabaseMultiTenantProvider");
        }
    }

    @Override
    public void start() {
        for (TenantIdSource tenantId : TenantIdSource.values()) {

            DataSource dataSource =
                    (DataSource) jndiService.locate(String.format(DATA_SOURCE_LOOKUP, tenantId.getDataSource()));

            if (dataSource == null) {
                throw new HibernateException(String.format(ERROR_DATA_SOURCE_LOOKUP, tenantId.getDataSource()));
            }

            dataSourceMap.put(tenantId.name(), dataSource);
        }
    }

    @Override
    public void stop() {
        dataSourceMap.clear();
    }

    @Override
    public DataSource selectAnyDataSource() {
        return dataSourceMap.values().iterator().next();
    }

    @Override
    public DataSource selectDataSource(String tenantIdentifier) {
        return dataSourceMap.get(tenantIdentifier);
    }

}
