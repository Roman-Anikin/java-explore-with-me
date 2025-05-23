package explore.app.users;

import explore.app.users.dto.FullUserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
