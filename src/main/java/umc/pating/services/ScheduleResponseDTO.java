package umc.pating.services;

import lombok.AllArgsConstructor;
import lombok.*;
import umc.pating.entity.Schedule;
import umc.pating.entity.ScheduleDetail;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Long scheduleId;
    private Long userId;
    private Integer year;
    private List<ScheduleDetailResponseDTO> details; // 스케줄 세부 리스트 받아오게

    public ScheduleResponseDTO(Schedule schedule, List<ScheduleDetail> details) {
        this.scheduleId = schedule.getId();
        this.userId = schedule.getUser().getUserId();
        this.year = schedule.getYear();
        this.details = details.stream()
                .map(ScheduleDetailResponseDTO::new)
                .collect(Collectors.toList());
    }

}
