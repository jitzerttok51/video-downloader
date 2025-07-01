package org.example;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import org.example.update.factory.UpdateRepositoryFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.Objects;
import java.util.stream.Collectors;

@Command(name = "demo", description = "...", mixinStandardHelpOptions = true)
public class DemoCommand implements Runnable {

    @Inject
    private AppInfoService appInfoService;

    private UpdateRepositoryFactory updateRepositoryFactory;

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(DemoCommand.class, args);
    }

    @Inject
    public void setAppInfoService(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @Inject
    public void setUpdateRepositoryFactory(UpdateRepositoryFactory updateRepositoryFactory) {
        this.updateRepositoryFactory = updateRepositoryFactory;
    }

    public void run() {
        // business logic here
        System.out.println("Test! "+appInfoService.getVersion());
        if (verbose) {
            System.out.println("Hi!");
        }

        var repo = updateRepositoryFactory
                .retrieveUpdateRepository("github:jitzerttok51/video-downloader");
        System.out.println(repo.getUpdates().stream().map(Objects::toString).collect(Collectors.joining("\n")));
    }
}

