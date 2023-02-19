package com.bts.quickstartspring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;
import picocli.CommandLine.*;

/**
 * from module directory use with
 * ./mvnw compile spring-boot:run -Dspring-boot.run.arguments="'Hello,how are you?'"
 */
@SpringBootApplication
public class QuickstartSpringApplication implements CommandLineRunner {

    private IFactory factory;
    private MessageCommand messageCommand;
    private int exitCode;

    // constructor injection
    QuickstartSpringApplication(IFactory factory, MessageCommand messageCommand) {
        this.factory = factory;
        this.messageCommand = messageCommand;
    }

    @Override
    public void run(String... args) {
        // let picocli parse command line args and run the business logic
        exitCode = new CommandLine(messageCommand, factory).execute(args);
    }

    public int getExitCode() {
        return exitCode;
    }

    public static void main(String[] args) {
        SpringApplication.run(QuickstartSpringApplication.class, args);
    }

}
