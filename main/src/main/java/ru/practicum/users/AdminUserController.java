package ru.practicum.users;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.FullUserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Validated
public class AdminUserController {

    private final UserService service;

    public AdminUserController(UserService service) {
        this.service = service;
    }

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
