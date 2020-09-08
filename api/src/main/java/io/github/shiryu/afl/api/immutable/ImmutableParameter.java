package io.github.shiryu.afl.api.immutable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImmutableParameter {

    private final String name;
    private final boolean optional;
    private final boolean sender;
    private final Class<?> elementType;
}
