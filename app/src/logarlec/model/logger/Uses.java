package logarlec.model.logger;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Uses {
    String[] fields();
}
