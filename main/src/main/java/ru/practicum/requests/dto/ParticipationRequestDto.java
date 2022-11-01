package ru.practicum.requests.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParticipationRequestDto {

    private Long id;
    private Long event;
    private Long requester;
    private String created;
    private String status;

}
