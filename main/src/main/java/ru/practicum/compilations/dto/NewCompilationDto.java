package ru.practicum.compilations.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCompilationDto {

    private Long id;
    private List<Long> events;
    private boolean pinned;
    private String title;

}
