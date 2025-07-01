package org.example.update.factory;

public class NoUpdateRepositoryDef extends RuntimeException {

    public NoUpdateRepositoryDef(String type) {
        super("No update repository defined for protocol of type: "+type);
    }
}
