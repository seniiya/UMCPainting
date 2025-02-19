package umc.pating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.pating.entity.Schedule;
import umc.pating.entity.ScheduleDetail;
import umc.pating.repository.ScheduleDetailRepository;
import umc.pating.repository.ScheduleRepository;
import umc.pating.services.ScheduleResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleDetailRepository scheduleDetailRepository;

    public ScheduleResponseDTO getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 스케줄이 없습니다."));

        List<ScheduleDetail> details = scheduleDetailRepository.findByScheduleId(scheduleId);

        return new ScheduleResponseDTO(schedule, details);
    }

}
