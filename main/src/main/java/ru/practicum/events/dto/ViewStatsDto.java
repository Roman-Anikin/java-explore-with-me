package ru.practicum.events.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ViewStatsDto {

    private String app;
    private String uri;
    private int hits;

}