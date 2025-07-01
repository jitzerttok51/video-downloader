package org.example.update.repository;

import org.example.update.model.Update;
import org.example.update.model.UpdatePart;

import java.nio.file.Path;
import java.util.List;

public interface UpdateRepository {

    List<Update> getUpdates(boolean snapshots);

    Path downloadUpdatePart(UpdatePart part, Path downloadDirectory);

    default List<Update> getUpdates() {
        return getUpdates(false);
    }
}
