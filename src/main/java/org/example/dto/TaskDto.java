package org.example.dto;

import lombok.*;
import org.example.domain.Status;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TaskDto {

    private String description;


    private Status status;
}
