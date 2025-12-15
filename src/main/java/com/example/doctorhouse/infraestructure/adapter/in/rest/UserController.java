package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.domain.port.in.LoginUserUseCase;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.infraestructure.adapter.in.dto.request.ChangePasswordRequestDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.request.LoginUserRequestDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.request.RegisterUserRequestDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.response.ChangePasswordResponseDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.response.LoginUserResponseDto;
import com.example.doctorhouse.infraestructure.adapter.in.dto.response.RegisterUserResponseDto;
import com.example.doctorhouse.infraestructure.adapter.in.mapper.UserRequestDtoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
        // Generar token
        String token = loginUserUseCase.login(requestDto.email(), requestDto.password());

        // Obtener usuario para el flag firstLogin
        Optional<UserModel> userOpt = loginUserUseCase.getUserByEmail(requestDto.email());
        boolean firstLogin = userOpt.map(UserModel::isFirstLogin).orElse(false);

        LoginUserResponseDto responseDto = new LoginUserResponseDto(token, firstLogin);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> register(
            @Valid @RequestBody RegisterUserRequestDto requestDto
    ) {
        UserModel userModel = mapper.toDomain(requestDto);
        registerUserUseCase.register(userModel);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new RegisterUserResponseDto(
                        userModel.getEmail(),
                        true,
                        "User registered successfully"
                ));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ChangePasswordResponseDto> changePassword(
            @Valid @RequestBody ChangePasswordRequestDto requestDto,
            Authentication authentication) {

        // Obtener email desde el JWT
        String email = authentication.getName();

        registerUserUseCase.changePassword(
                email,
                requestDto.oldPassword(),
                requestDto.newPassword()
        );

        return ResponseEntity.ok(new ChangePasswordResponseDto("Password changed successfully"));
    }

}
