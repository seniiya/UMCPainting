package umc.pating.services;

import lombok.AllArgsConstructor;
import lombok.*;
import umc.pating.entity.Schedule;
import umc.pating.entity.ScheduleDetail;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor

public class ScheduleDetailResponseDTO {
    private Long scheduleDetailId;
    private String day;
    private String title;
    private String location;
    private String startTime;
    private String endTime;

    public ScheduleDetailResponseDTO(ScheduleDetail scheduleDetail) {
        this.scheduleDetailId = scheduleDetail.getId();
        this.day = scheduleDetail.getDay();
        this.title = scheduleDetail.getTitle();
        this.location = scheduleDetail.getLocation();
        this.startTime = scheduleDetail.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        this.endTime = scheduleDetail.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}
