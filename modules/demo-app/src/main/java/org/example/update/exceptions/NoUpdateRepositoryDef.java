package org.example.update.exceptions;

public class NoUpdateRepositoryDef extends UpdateRepositoryException {

    public NoUpdateRepositoryDef(String type) {
        super("No update repository defined for protocol of type: "+type);
    }
}
