package org.example.DTO;

import lombok.*;
import org.example.domain.Status;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private String description;


    private Status status;
}
