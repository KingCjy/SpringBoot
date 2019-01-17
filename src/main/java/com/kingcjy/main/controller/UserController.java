package com.kingcjy.main.controller;

import com.kingcjy.main.dto.TestDTO;
import com.kingcjy.main.dto.UserDto;
import com.kingcjy.main.entity.UserEntity;
import com.kingcjy.main.service.UserService;
import com.kingcjy.main.util.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {

        UserEntity userEntity = userService.getUserByPhone("01053347385");

        return new ResponseEntity<>("pong", HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/join")
    public ResponseEntity<ApiResult<Void>> join(@RequestBody @Valid UserDto userDto) {

        userService.join(userDto);

        return ApiResult.getResponse(HttpStatus.OK, "success");
    }

    @ResponseBody
    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestBody @Valid TestDTO testDTO) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
