package org.rhc.server;

/**
 * Created with IntelliJ IDEA.
 * User: Jun
 * Date: 1/8/13
 * Time: 9:56 AM
 * To change this template use File | Settings | File Templates.
 */

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;
    private static final ServiceRegistry SERVICE_REGISTRY;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();
            SERVICE_REGISTRY = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            SESSION_FACTORY = configuration.buildSessionFactory(SERVICE_REGISTRY);
        } catch (HibernateException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
