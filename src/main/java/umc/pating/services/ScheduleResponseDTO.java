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
@NoArgsConstructor
public class ScheduleResponseDTO {
    private Long scheduleId;
    private Long userId;
    private Integer year;
    private List<ScheduleDetailResponseDTO> details; // 스케줄 세부 리스트 받아오게

//    // ✅ Schedule을 받아서 DTO로 변환하는 생성자 추가
    public ScheduleResponseDTO(Schedule schedule) {
        this.scheduleId = schedule.getId();
        this.userId = schedule.getUser().getId();
        this.year = schedule.getYear();
        this.details = null; // ✅ 기본적으로 null 처리, 필요하면 List<ScheduleDetail>도 변환 가능
    }


    public ScheduleResponseDTO(Schedule schedule, List<ScheduleDetail> details) {
        this.scheduleId = schedule.getId();
        this.userId = schedule.getUser().getUserId();
        this.year = schedule.getYear();
        this.details = details.stream()
                .map(ScheduleDetailResponseDTO::new)
                .collect(Collectors.toList());
    }

}
