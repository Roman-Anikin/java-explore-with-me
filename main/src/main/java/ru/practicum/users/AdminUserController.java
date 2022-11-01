package ru.practicum.users;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import ru.practicum.users.dto.FullUserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/admin/users")
@Validated
public class AdminUserController {

    private final UserService service;

    @GetMapping
    public List<FullUserDto> get(@RequestParam(value = "ids", required = false) Long[] ids,
                                 @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                                 @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return service.get(ids, from, size);
    }

    @PostMapping
    public FullUserDto add(@Valid @RequestBody FullUserDto userDto) {
        return service.add(userDto);
    }

    @DeleteMapping(path = "/{userId}")
    public void remove(@PathVariable Long userId) {
        service.remove(userId);
    }
}
