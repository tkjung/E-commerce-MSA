package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Greeting;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // REST 방식의 데이터 처리하기 위해 사용. REST 란, 1URI = 1리소스를 대표
@RequestMapping("/")
public class UserController {
    private Environment env;
    private UserService userService;

    @Autowired
    private Greeting greeting;

    @Autowired
    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        // return env.getProperty("greeting.message");
        return greeting.getMessage();
    }


    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserEntity> userList = userService.getUserByAll(); // 모든 유저 가져옴. UserEntity 형식 여러 개는 userList 가 된다.

        List<ResponseUser> result = new ArrayList<>(); // 새 배열 result 의 자료형은 ResponseUser 인데
        userList.forEach(v ->{                         // userList 의 하나하나 userEntity 에 대하여
            result.add(new ModelMapper().map(v,ResponseUser.class)); // result 에다 추가해준다. 추가할 땐 ResponseUser 로 변환하고 추가.
        });

        return ResponseEntity.status(HttpStatus.OK).body(result); // 리턴은 body 부분에 result 를 담아 반환.
    }


}
