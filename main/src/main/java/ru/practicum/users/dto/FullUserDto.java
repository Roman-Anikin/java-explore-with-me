package ru.practicum.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullUserDto {

    private Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @NotEmpty(message = "Имя не должно быть пустым")
    private String name;

    @Email
    private String email;
}
