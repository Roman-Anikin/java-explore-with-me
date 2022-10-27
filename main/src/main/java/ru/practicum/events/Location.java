package ru.practicum.events;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class Location {

    private Float lat;
    private Float lon;
}
