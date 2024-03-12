package Managers;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConnectionSetUper {
    private SessionFactory sessionFactory;
    private HibernateConfigurationFilePaths hibernateConfigurationFilePaths = new HibernateConfigurationFilePaths();

    public SessionFactory setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(hibernateConfigurationFilePaths.HIBERNATE_CONFIGURATIONS)
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            System.err.println("some troubles with building session factory: " + e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }

        return null;
    }
}
