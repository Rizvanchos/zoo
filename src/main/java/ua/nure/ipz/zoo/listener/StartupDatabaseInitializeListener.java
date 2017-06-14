package ua.nure.ipz.zoo.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import ua.nure.ipz.zoo.demo.GeneratorController;
import ua.nure.ipz.zoo.exception.DomainLogicException;

@Component
public class StartupDatabaseInitializeListener implements ApplicationContextAware{

    private static final String JUNIT_PERSISTENCE_UNIT_NAME = "zooJUnit";

    @Autowired
    private GeneratorController generatorController;
    private ApplicationContext applicationContext;

    @EventListener(ContextRefreshedEvent.class)
    public void initialize() throws DomainLogicException {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = applicationContext.getBean(LocalContainerEntityManagerFactoryBean.class);
        if(shouldInitialize(localContainerEntityManagerFactoryBean)) {
            generatorController.run();
        }
    }

    private boolean shouldInitialize(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        return !localContainerEntityManagerFactoryBean.getPersistenceUnitName().equals(JUNIT_PERSISTENCE_UNIT_NAME);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
