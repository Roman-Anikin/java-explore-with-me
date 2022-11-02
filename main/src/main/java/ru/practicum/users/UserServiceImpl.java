package ru.practicum.users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exceptions.ObjectNotFoundException;
import ru.practicum.exceptions.ValidationException;
import ru.practicum.users.dto.FullUserDto;
import ru.practicum.utils.OffsetPageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository repository;

    @Override
    public FullUserDto add(FullUserDto userDto) {
        checkEmail(userDto.getEmail());
        User user = repository.save(userMapper.fromDto(userDto));
        log.info("Добавлен пользователь {}", user);
        return userMapper.toDto(user);
    }

    @Override
    public List<FullUserDto> get(Long[] ids, Integer from, Integer size) {
        List<User> users;
        if (ids == null) {
            users = repository.findAll(getPageable(from, size)).getContent();
        } else {
            users = repository.findAllById(Arrays.asList(ids));
        }
        log.info("Получен список пользователей {}", users);
        return userMapper.toDto(users);
    }

    @Override
    public void remove(Long userId) {
        checkUser(userId);
        repository.deleteById(userId);
        log.info("Пользователь с id {} удален", userId);
    }

    @Override
    public User getById(Long userId) {
        return checkUser(userId);
    }

    private User checkUser(Long userId) {
        Optional<User> user = repository.findById(userId);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException("Пользователь с id " + userId + " не найден");
        }
        return user.get();
    }

    private void checkEmail(String email) {
        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new ValidationException("Почта не может быть пустой");
        }
    }

    private Pageable getPageable(Integer from, Integer size) {
        return new OffsetPageRequest(from, size, Sort.unsorted());
    }
}
