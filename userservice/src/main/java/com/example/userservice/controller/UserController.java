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

    @GetMapping("/welcome")
    public String welcome() {
        // return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) { // ResponseEntity: 개발자가 직접 결과데이터와 HTTP상태코드 제어
        ModelMapper mapper = new ModelMapper();    // RequestBody: Http 메시지 컨버터가 Http 메시지 바디의 내용을 우리가 원하는 객체/문자로 변환해줌.
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // STRICT: 기본값이 아닌 잘못 매칭되는 경우를 피하기 위해.

        UserDto userDto = mapper.map(user, UserDto.class);
        userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }




}
