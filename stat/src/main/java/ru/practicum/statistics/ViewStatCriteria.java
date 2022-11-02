package ru.practicum.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewStatCriteria {

    private String start;
    private String end;
    private String[] uris;
    private boolean unique;

}
