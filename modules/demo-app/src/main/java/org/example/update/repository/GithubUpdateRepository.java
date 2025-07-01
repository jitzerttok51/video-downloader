package org.example.update.repository;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.example.update.model.GithubRelease;
import org.example.update.model.Update;
import org.example.update.model.UpdatePart;
import org.example.update.utils.GithubCommonsInterceptor;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class GithubUpdateRepository implements UpdateRepository {

    private final String repository;

    private final OkHttpClient client;

    private final ObjectMapper mapper;

    public GithubUpdateRepository(String repository, OkHttpClient client, ObjectMapper mapper) {
        this.repository = repository;
        this.client = client.newBuilder()
                .addInterceptor(new GithubCommonsInterceptor())
                .build();
        this.mapper = mapper;
    }

    @Override
    public List<Update> getUpdates(boolean snapshots) {
        Request request = new Request.Builder().url(getURL()).build();
        try(var response = client.newCall(request).execute()) {
            if(response.code() != 200) {
                throw new RuntimeException(response.message());
            }

            // TODO: Exception if invalid format
            var body = mapper.readValue(response.body().string(), Argument.listOf(GithubRelease.class));
            return body.stream()
                    .map(GithubRelease::toUpdate)
                    .toList();
            // TODO: Order and filter
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getURL() {
        return "https:/api.github.com/repos/" + repository + "/releases";
    }

    @Override
    public Path downloadUpdatePart(UpdatePart part, Path downloadDirectory) {
        // TODO: Implement
        throw new RuntimeException("Not implemented");
    }
}
