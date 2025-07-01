package org.example.update.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;

@Serdeable
public record GithubReleaseAsset(
        long id,
        String name,
        @JsonProperty("browser_download_url") String downloadURL,
        String digest,
        long size,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt) {

    public UpdatePart toUpdatePart() {
        String[] segments = name.replace(".exe", "").split("-");
        return new UpdatePart(segments[segments.length - 1], downloadURL, name, digest);
    }
}
