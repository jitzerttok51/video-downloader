package org.example;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "demo", description = "...", mixinStandardHelpOptions = true)
public class DemoCommand implements Runnable {

    @Inject
    private AppInfoService appInfoService;

    @Option(names = {"-v", "--verbose"}, description = "...")
    boolean verbose;

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(DemoCommand.class, args);
    }

    public void run() {
        // business logic here
        System.out.println("Test! "+appInfoService.getVersion());
        if (verbose) {
            System.out.println("Hi!");
        }
    }
}

