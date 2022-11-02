package ru.practicum.statistics.dto;

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
