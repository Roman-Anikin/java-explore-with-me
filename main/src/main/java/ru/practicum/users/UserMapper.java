package ru.practicum.users;

import org.springframework.stereotype.Component;
import ru.practicum.users.dto.FullUserDto;
import ru.practicum.users.dto.ShortUserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public FullUserDto toDto(User user) {
        return new FullUserDto(user.getId(), user.getName(), user.getEmail());
    }

    public User fromDto(FullUserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getEmail());
    }

    public ShortUserDto toShortDto(User user) {
        return new ShortUserDto(user.getId(), user.getName());
    }

    public List<FullUserDto> toDto(List<User> users) {
        return users
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
