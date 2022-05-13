package com.github.friendlytrainer

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}