package umc.pating.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.pating.entity.Schedule;
import umc.pating.entity.ScheduleDetail;
import umc.pating.entity.User;
import umc.pating.repository.ScheduleDetailRepository;
import umc.pating.repository.ScheduleRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.ScheduleDetailRequestDTO;
import umc.pating.services.ScheduleDetailResponseDTO;

@Service
@RequiredArgsConstructor
public class ScheduleDetailService {
    private final ScheduleDetailRepository scheduleDetailRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    // 스케줄 조회 기능
    public ScheduleDetailResponseDTO getScheduleDetail(Long detailId) {
        ScheduleDetail detail = scheduleDetailRepository.findById(detailId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다: " + detailId));

        return new ScheduleDetailResponseDTO(detail);
    }

    // 일정 추가
    public ScheduleDetailResponseDTO createScheduleDetail(ScheduleDetailRequestDTO requestDTO) {

        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다: " + requestDTO.getUserId()));


        Schedule schedule = scheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄 존재하지 않습니다."+ requestDTO.getScheduleId()));


        ScheduleDetail scheduleDetail = ScheduleDetail.builder()
                .user(user)
                .title(requestDTO.getTitle())
                .location(requestDTO.getLocation())
                .day(requestDTO.getDay())
                .startTime(requestDTO.getStartTime())
                .endTime(requestDTO.getEndTime())
                .schedule(schedule)
                .build();

        scheduleDetailRepository.save(scheduleDetail);
        return new ScheduleDetailResponseDTO(scheduleDetail);

    }

    // 특정 일정 수정
    public ScheduleDetailResponseDTO updateScheduleDetail(Long detailId, ScheduleDetailRequestDTO requestDTO) {
        ScheduleDetail detail = scheduleDetailRepository.findById(detailId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 없습니다"));

        detail.setTitle(requestDTO.getTitle());
        detail.setLocation(requestDTO.getLocation());
        detail.setDay(requestDTO.getDay());
        detail.setStartTime(requestDTO.getStartTime());
        detail.setEndTime(requestDTO.getEndTime());

        scheduleDetailRepository.save(detail);
        return new ScheduleDetailResponseDTO(detail);
    }

    //특정 일정 삭제
    public void deleteScheduleDetail(Long detailId) {
        scheduleDetailRepository.deleteById(detailId);
    }
}
