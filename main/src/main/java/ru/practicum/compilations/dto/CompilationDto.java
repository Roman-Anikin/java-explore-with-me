package ru.practicum.compilations.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import ru.practicum.events.dto.ShortEventDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CompilationDto {

    private Long id;
    private List<ShortEventDto> events;
    private boolean pinned;
    private String title;

}
