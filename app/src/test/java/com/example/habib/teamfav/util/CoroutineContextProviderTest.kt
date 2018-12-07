package com.example.habib.teamfav.util

import kotlinx.coroutines.experimental.Unconfined
import org.junit.Test

import org.junit.Assert.*
import kotlin.coroutines.experimental.CoroutineContext

class ContextProviderTest: CoroutineContextProvider() {
    override val main: CoroutineContext = Unconfined
}