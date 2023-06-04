package me.youzhilane.dojo;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;

import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.*;

@Slf4j
public class Agent {
    private static String RESTCONTROLLER_ANNOTATION ="org.springframework.web.bind.annotation.RestController";
    private static String SERVICE_ANNOTATION="org.springframework.stereotype.Service";

    //$ java -javaagent:target/bytebuddy-demo-1.0-SNAPSHOT-jar-with-dependencies.jar -jar target_jar
    public static void premain(String args, Instrumentation instrumentation) {
        log.info("Enter premain, args:{}",args);
        AgentBuilder builder = new AgentBuilder.Default()
                .ignore(nameStartsWith("net.bytebuddy")
                        .or(nameStartsWith("org.apache")))
                .type(isAnnotatedWith(named(RESTCONTROLLER_ANNOTATION)
                        .or(named(SERVICE_ANNOTATION))))
                .transform(new AgentTransformer())
                .with(new ByteByddyListener());
        builder.installOn(instrumentation);
        log.info("Leave premain, args:{}",args);
    }


}
