package com.elder.scrabble.core

import reactor.core.publisher.Mono
import java.util.Optional

fun <T> Optional<T>.toMonoOrEmpty(): Mono<T> = Mono.justOrEmpty(this)

fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)
