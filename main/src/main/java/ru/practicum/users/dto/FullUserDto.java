package ru.practicum.users.dto;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FullUserDto {

    private Long id;
    private String name;

    @Email
    private String email;
}
