package com.example.cellularfilling.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(otherFlow: Flow<T>): Flow<T> {
    return merge(this, otherFlow)
}