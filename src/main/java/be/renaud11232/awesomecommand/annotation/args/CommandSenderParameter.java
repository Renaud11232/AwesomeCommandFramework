package be.renaud11232.awesomecommand.annotation.args;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CommandSenderParameter {
}
