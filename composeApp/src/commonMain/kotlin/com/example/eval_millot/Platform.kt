package com.example.eval_millot

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform