package ru.practicum.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventCriteria {

    private String text;
    private Long[] categories;
    private Long[] users;
    private String[] states;
    private Boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private boolean onlyAvailable = false;
    private String sort;
    private Integer from = 0;
    private Integer size = 10;

}
