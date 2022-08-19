package com.evavrynchuk.multitenancy.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseEntityManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityManager.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        if (entityManager == null) {
            LOGGER.info("Creating new entity manager");
            entityManager = entityManagerFactory.createEntityManager();
        }

        return entityManager;
    }
}
