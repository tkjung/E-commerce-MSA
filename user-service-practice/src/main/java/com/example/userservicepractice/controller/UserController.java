package com.example.userservicepractice.controller;

import com.example.userservicepractice.vo.Greeting;
import com.example.userservicepractice.vo.RequestUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {
    private Environment env;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health-check")
    public String status() {
        return "It's working in user service";
    }

    @GetMapping("/welcome")
    public String welcome() {
        // return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        return "Create user method is called";
    }
}
