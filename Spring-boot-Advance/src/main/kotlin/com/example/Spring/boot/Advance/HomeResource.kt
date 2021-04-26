package com.example.Spring.boot.Advance

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeResource {
    @GetMapping("/")
    fun home():String{
        return "home"
    }

    @GetMapping("/user")
    fun  user():String{
        return "user"
    }

    @GetMapping("/admin")
    fun hadmin():String{
        return "admin"
    }

}