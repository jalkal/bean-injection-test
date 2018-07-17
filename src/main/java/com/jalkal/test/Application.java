package com.jalkal.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Service service = context.getBean(Service.class);
        service.run();
    }
}

@Configuration
class AppConfig{

    @Bean
    public Service getService(){
        return new ServiceImpl(getRepository(), getClient());
    }

    @Bean
    public Client getClient(){
        return new ClientImpl(getRepository());
    }

    public Repository getRepository() {
        return new RepositoryImpl("database");
    }
}

interface Service{
    void run();
}

interface Client{
    void call();
}

interface Repository{
    void get();
}

class ServiceImpl implements Service{

    private final Repository repository;
    private final Client client;

    ServiceImpl(Repository repository, Client client) {
        this.repository = repository;
        this.client = client;
    }

    public void run() {
        System.out.println("Executing service");
        this.repository.get();
        this.client.call();
    }
}

class RepositoryImpl implements Repository{

    private final String database;
    private int numberExec = 0;

    RepositoryImpl(String database) {
        this.database = database;
    }

    public void get() {
        this.numberExec++;
        System.out.println("Repo getting data from " + this.database + " executed " + numberExec + " times");
    }
}

class ClientImpl implements Client{

    private final Repository repository;

    ClientImpl(Repository repository) {
        this.repository = repository;
    }

    public void call() {
        this.repository.get();
    }
}