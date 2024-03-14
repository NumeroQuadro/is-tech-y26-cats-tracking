package Managers;

import Repositories.OwnerRepository;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateConnectionSetUper {
    private static final Logger logger = LoggerFactory.getLogger(HibernateConnectionSetUper.class);
    private HibernateConfigurationFilePaths hibernateConfigurationFilePaths = new HibernateConfigurationFilePaths();

    public EntityManagerFactory setUpWithPersistenceJPA() {
        try {
            return Persistence.createEntityManagerFactory(hibernateConfigurationFilePaths.PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            logger.error("problem was occured trying to set up EntityManagerFactory", e);
        }

        return null;
    }

    public EntityManagerFactory setUpWithHibernate() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure(hibernateConfigurationFilePaths.HIBERNATE_CONFIGURATIONS)
                    .build();
            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            logger.error("problem was occured trying to set up EntityManagerFactory", e);
        }

        return null;
    }
}
