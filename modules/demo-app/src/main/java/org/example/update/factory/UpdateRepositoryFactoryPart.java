package org.example.update.factory;

import org.example.update.repository.UpdateRepository;

public interface UpdateRepositoryFactoryPart {
    String getProtocol();

    UpdateRepository initializeRepository(String url);
}
