package com.kingcjy.main.controller;

import com.kingcjy.main.dto.TestDTO;
import com.kingcjy.main.dto.UserDTO;
import com.kingcjy.main.entity.UserEntity;
import com.kingcjy.main.service.UserService;
import com.kingcjy.main.domain.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/ping")
    public ResponseEntity<ApiResult<String>> ping(HttpServletRequest request) {
        UserEntity userEntity = userService.getUserByPhone("01053347385");
        return ApiResult.getResponse(HttpStatus.OK, "success", "pong");
    }

    @ResponseBody
    @PostMapping("/join")
    public ResponseEntity<ApiResult<Void>> join(@RequestBody @Valid UserDTO userDto) {
        userService.join(userDto);

        return ApiResult.getResponse(HttpStatus.OK, "success");
    }

    @ResponseBody
    @PostMapping("/test")
    public ResponseEntity<Void> test(@RequestBody @Valid TestDTO testDTO) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
