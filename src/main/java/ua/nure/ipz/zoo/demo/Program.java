package ua.nure.ipz.zoo.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import ua.nure.ipz.zoo.exception.DomainLogicException;

public class Program {

    public static void main(String[] args) throws DomainLogicException {
        ApplicationContext context = new FileSystemXmlApplicationContext("web/WEB-INF/config/spring-core-config.xml");
        GeneratorController generatorController = context.getBean(GeneratorController.class);
        generatorController.run();
    }
}