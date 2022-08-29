package erisnilton.dev.admin.catalogo;

import erisnilton.dev.admin.catalogo.infraestrutura.configuration.ObjectMapperConfig;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@JsonTest(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ObjectMapperConfig.class)
})
public @interface JacksonTest {
}
