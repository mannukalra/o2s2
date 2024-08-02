package org.o2s.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.o2s.orm.FruitResource;

@ApplicationScoped
public class GreetingService {

    @Inject
    FruitResource fruitResource;

    public String greeting(String name) {
        return "hello " + name + fruitResource.get().toString();
    }

}
