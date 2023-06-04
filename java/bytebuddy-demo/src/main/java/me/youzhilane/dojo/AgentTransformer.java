package me.youzhilane.dojo;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Morph;
import net.bytebuddy.utility.JavaModule;

import java.security.ProtectionDomain;

import static net.bytebuddy.matcher.ElementMatchers.*;

@Slf4j
public class AgentTransformer implements AgentBuilder.Transformer {
    @Override
    public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder,
                                            TypeDescription typeDescription,
                                            ClassLoader classLoader,
                                            JavaModule module,
                                            ProtectionDomain protectionDomain) {
        log.info("actualName to transform:{}",typeDescription.getActualName());
        String MAPPING_PKG_PREFIX="org.springframework.web.bind.annotation";
        String MAPPING_SUFFIX="Mapping";
        DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?> intercept = builder.method(
                not(isStatic()).and(isAnnotatedWith(nameStartsWith(MAPPING_PKG_PREFIX)
                        .and(nameEndsWith(MAPPING_SUFFIX))))
        ).intercept(MethodDelegation
                .withDefaultConfiguration()
                .withBinders(Morph.Binder.install(MyCallable.class))
                .to(new DemoInterceptor()));
        return intercept;
    }
}
