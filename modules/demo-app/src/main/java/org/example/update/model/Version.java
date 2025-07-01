package org.example.update.model;

import org.jetbrains.annotations.NotNull;

public record Version(
        int major,
        int minor,
        int build) implements Comparable<Version> {

    @Override
    public int compareTo(@NotNull Version o) {
        var result = Integer.compare(major(), o.major());
        if(result == 0) {
            result = Integer.compare(minor(), o.minor());
            if(result == 0) {
                result = Integer.compare(build(), o.build());
            }
        }

        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return major+"."+minor+"."+build;
    }
}
