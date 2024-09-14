package org.o2s.api;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.o2s.orm.DataResource;

@ApplicationScoped
public class GreetingService {

    @Inject
    DataResource dataResource;

    public String greeting(String name) {
        return "hello " + name + dataResource.get().toString();
    }

}
