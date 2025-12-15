package com.example.doctorhouse.infrastructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.UserMapper;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.UserRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        User domainUser = userMapper.toDomain(request);
        User registeredUser = registerUserUseCase.registerUser(domainUser);
        return new ResponseEntity<>(userMapper.toResponse(registeredUser), HttpStatus.CREATED);
    }
}
