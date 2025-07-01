package org.example.update.factory;

import io.micronaut.context.BeanProvider;
import jakarta.inject.Singleton;
import org.example.update.repository.UpdateRepository;

import java.net.URI;
import java.util.Optional;

@Singleton
public class UpdateRepositoryFactory {

    private final BeanProvider<UpdateRepositoryFactoryPart> parts;

    public UpdateRepositoryFactory(BeanProvider<UpdateRepositoryFactoryPart> parts) {
        this.parts = parts;
    }

    public UpdateRepository retrieveUpdateRepository(String url) {
        String protocol = URI.create(url).getScheme();
        String path = url.replace(protocol+":", "");
        return get(protocol)
                .map(part -> part.initializeRepository(path))
                .orElseThrow(() -> new NoUpdateRepositoryDef(protocol));
    }

    private Optional<UpdateRepositoryFactoryPart> get(String protocol) {
        return parts.stream()
                .filter(p -> protocol.equals(p.getProtocol()))
                .findFirst();
    }
}
