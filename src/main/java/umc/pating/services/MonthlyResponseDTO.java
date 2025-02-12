package umc.pating.services;


import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
public class MonthlyResponseDTO {
    private LocalDate date;
    private String displayImage;
}

