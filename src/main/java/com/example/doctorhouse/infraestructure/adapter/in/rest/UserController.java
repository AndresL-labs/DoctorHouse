package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.domain.port.in.LoginUserUseCase;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.infraestructure.adapter.in.dto.request.LoginUserRequestDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.request.RegisterUserRequestDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.response.LoginUserResponseDto;
import com.example.doctorhouse.infraestructure.adapter.in.mapper.UserRequestDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final LoginUserUseCase loginUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UserRequestDtoMapper mapper;

    public UserController(LoginUserUseCase loginUserUseCase, RegisterUserUseCase registerUserUseCase, UserRequestDtoMapper mapper) {
        this.loginUserUseCase = loginUserUseCase;
        this.registerUserUseCase = registerUserUseCase;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@Valid @RequestBody LoginUserRequestDto requestDto) {
       String token  = loginUserUseCase.login(requestDto.email(), requestDto.password());
       LoginUserResponseDto responseDto = new LoginUserResponseDto(token);

        return new ResponseEntity<>(responseDto, HttpStatus.valueOf(200));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserRequestDto requestDto) {
        UserModel userModel = mapper.toDomain(requestDto);
        registerUserUseCase.register(userModel);

        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }
}
