package logarlec.model.logger;

import java.lang.annotation.*;

/**
 * @warning This annotation can only be used on the class versions of the build
 *          in types
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface State {
    String name();

    int min() default 0;

    int max() default 0;
}
