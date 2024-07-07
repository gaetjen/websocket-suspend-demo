package de.johannesgaetjen

import jakarta.inject.Singleton

abstract class OnOpenCounter {
    var count: Int = 0
        private set

    fun increment() {
        count++
    }
}

@Singleton
class BlockingOnOpenCounter : OnOpenCounter()

@Singleton
class SuspendingOnOpenCounter : OnOpenCounter()