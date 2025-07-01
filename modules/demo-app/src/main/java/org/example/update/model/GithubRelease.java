package org.example.update.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Serdeable
public record GithubRelease(
        long id,
        @JsonProperty("tag_name") String tagName,
        boolean prerelease,
        List<GithubReleaseAsset> assets
) {

    public Update toUpdate() {
        String ver = tagName.replace("v", "");
        String[] comp = ver.split("\\.");
        // TODO: Verify version
        Version version = new Version(
                Integer.parseInt(comp[0]),
                Integer.parseInt(comp[1]),
                Integer.parseInt(comp[2])
        );
        Set<UpdatePart> parts = assets.stream()
                .map(GithubReleaseAsset::toUpdatePart)
                .collect(Collectors.toSet());
        return new Update(version, prerelease ? UpdateType.SNAPSHOT : UpdateType.RELEASE, parts);
    }
}
