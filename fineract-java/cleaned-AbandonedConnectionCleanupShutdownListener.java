
package org.apache.fineract.infrastructure.core.service;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import org.apache.fineract.infrastructure.jobs.service.JobRegisterServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;
@Service
public class AbandonedConnectionCleanupShutdownListener implements ApplicationListener<ContextClosedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(AbandonedConnectionCleanupShutdownListener.class);
    @Override
    public void onApplicationEvent(@SuppressWarnings("unused") ContextClosedEvent event) {
        shutDowncleanUpThreadAndDeregisterJDBCDriver();
    }
    private void shutDowncleanUpThreadAndDeregisterJDBCDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                java.sql.DriverManager.deregisterDriver(driver);
                LOG.info("JDBC driver de-registered successfully");
            } catch (Throwable t) {
                LOG.error("Exception occured while deristering jdbc driver", t);
            }
        }
        try {
            Thread.sleep(2000L);
        } catch (Exception e) {
            LOG.error("Exception Occcured while trying to sleep.", e);
        }
    }
}
