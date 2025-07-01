package org.example.update.model;

public record UpdatePart(
        String os,
        String url,
        String name,
        String checksum) {
}
