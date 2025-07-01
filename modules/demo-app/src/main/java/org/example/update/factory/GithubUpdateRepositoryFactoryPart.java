package org.example.update.factory;

import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import org.example.update.repository.GithubUpdateRepository;
import org.example.update.repository.UpdateRepository;

@Singleton
public class GithubUpdateRepositoryFactoryPart implements UpdateRepositoryFactoryPart {

    private final OkHttpClient client;

    private final ObjectMapper mapper;

    public GithubUpdateRepositoryFactoryPart(OkHttpClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public String getProtocol() {
        return "github";
    }

    @Override
    public UpdateRepository initializeRepository(String url) {
        return new GithubUpdateRepository(url.replace(":"+getProtocol(), ""), client, mapper);
    }
}
