@file:Suppress("UNCHECKED_CAST")

package com.fphoenixcorneae.baseui.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer

inline fun <reified T> invokeCompose(
    composer: Composer,
    noinline function: @Composable () -> T
): T {
    return (function as Function2<Composer, Int, Any?>).invoke(composer, 100000) as T
}

inline fun <reified T, A> invokeCompose(
    composer: Composer,
    noinline function: @Composable (A?) -> T?,
    p1: A?
): T? {
    return (function as Function3<Any?, Composer, Int, Any?>).invoke(p1, composer, 100000) as T?
}

inline fun <reified T, A, B> invokeCompose(
    composer: Composer,
    noinline function: @Composable (A?, B?) -> T?,
    p1: A?,
    p2: B?
): T? {
    return (function as Function4<Any?, Any?, Composer, Int, Any?>).invoke(
        p1,
        p2,
        composer,
        100000
    ) as T?
}