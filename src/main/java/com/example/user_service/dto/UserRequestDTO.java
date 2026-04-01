package com.example.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
    @Size(min = 6, max = 20, message = "Username from 6 to 20 letters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username just have words, number, underline '_'")
    private String username;

    private String avatarUrl;
}
