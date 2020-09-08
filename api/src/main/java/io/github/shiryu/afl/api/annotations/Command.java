package io.github.shiryu.afl.api.annotations;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    @NotNull
    String[] aliases();

    @NotNull
    String description() default "";

    @NotNull
    String[] permissions() default "";

    @NotNull
    String usage() default "";

    @NotNull
    Target target() default Target.BOTH;

    enum Target{

        PLAYER,
        CONSOLE,
        BOTH

    }

}
