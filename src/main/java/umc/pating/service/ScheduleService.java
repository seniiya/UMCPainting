package umc.pating.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.pating.entity.Schedule;
import umc.pating.entity.ScheduleDetail;
import umc.pating.entity.User;
import umc.pating.repository.ScheduleDetailRepository;
import umc.pating.repository.ScheduleRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.ScheduleResponseDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleDetailRepository scheduleDetailRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDTO getUserSchedule(Long userId, Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 Id의 스케줄이 없습니다."));

        // ✅ 유저 ID 검증 추가
        if (!schedule.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("해당 스케줄을 조회할 권한이 없습니다.");
        }

        List<ScheduleDetail> details = scheduleDetailRepository.findByScheduleId(scheduleId);

        return new ScheduleResponseDTO(schedule, details);
    }

    // scheduleId 자동 증가
    @Transactional
    public ScheduleResponseDTO getOrCreateSchedule(Long userId, int year) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ 기존 유저의 joinYear가 null이면 현재 연도로 설정
        if (user.getJoinYear() == null) {
            int currentYear = LocalDate.now().getYear();
            user.setJoinYear(currentYear);
            userRepository.save(user);  // 🔥 변경사항 저장
        }

        int joinYear = user.getJoinYear();  // ✅ 가입 연도 가져오기

        // scheduleId 계산: 현재 연도 - 가입 연도 + 1
        int scheduleId = (year - joinYear) + 1;

        // 해당 연도에 이미 존재하는지 확인
        Optional<Schedule> existingSchedule = scheduleRepository.findByUserIdAndYear(userId, year);
        Schedule schedule;

        if (existingSchedule.isPresent()) {
            schedule = existingSchedule.get();
        } else {
            // 새 스케줄 생성
            schedule = Schedule.builder()
                    .user(user)
                    .year(year)
//                    .id((long) scheduleId) // ✅ scheduleId 자동 증가
                    .build();
            scheduleRepository.save(schedule);
        }

        List<ScheduleDetail> details = scheduleDetailRepository.findByScheduleId(schedule.getId());


        return new ScheduleResponseDTO(schedule, details);
    }


}
