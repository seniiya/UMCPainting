package umc.pating.services;

import lombok.AllArgsConstructor;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleDetailRequestDTO {
    private Long userId;
    private String title;
    private String location;
    private String day;
    private Long scheduleId;
    private LocalTime startTime;
    private LocalTime endTime;

}
