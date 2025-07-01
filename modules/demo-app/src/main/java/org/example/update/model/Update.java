package org.example.update.model;

import java.util.Set;

public record Update(
        Version version,
        UpdateType type,
        Set<UpdatePart> parts) {
}
