package ru.practicum.users;

import ru.practicum.users.dto.FullUserDto;

import java.util.List;

public interface UserService {

    FullUserDto add(FullUserDto userDto);

    List<FullUserDto> get(Long[] ids, Integer from, Integer size);

    void remove(Long userId);

    User getById(Long userId);

}
