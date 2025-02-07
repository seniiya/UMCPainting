package umc.pating.service;


import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.Daily;
import umc.pating.entity.User;
import umc.pating.repository.DailyRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.DailyRequestDTO;
import umc.pating.services.DailyResponseDTO;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyService {
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;

    // 현재 로그인한 유저 가져오기
    private User getCurrentUser(Long userId) {
        //이거 시큐리티로 로그인한 유저 이메일로 찾기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName(); // 기본적으로 username(email) 반환됨
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("인증된 유저 정보를 찾을 수 없습니다."));
    }

    // daily 기록 조회
    @Transactional(readOnly = true)
    public DailyResponseDTO getDaily(Long userId, LocalDate date) {
        System.out.println("🔍 userId: " + userId + ", date: " + date); // 디버깅 로그 추가
        Daily daily = dailyRepository.findByUserIdAndDailyDayRecording(userId, date)
                .orElseThrow(() -> new RuntimeException("해당 날짜의 기록이 없습니다."));
        return new DailyResponseDTO(daily);
    }


    // Daily 기록 작성 + 수정
    public DailyResponseDTO saveDaily(Long userId, DailyRequestDTO request) {
        System.out.println("🔍 userId from request: " + request.getUserId()); // 디버깅 로그 추가
        System.out.println("🔍 userId from parameter: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Optional<Daily> existingDaily = dailyRepository.findByUserIdAndDailyDayRecording(user.getId(), request.getDailyDayRecording());


        Daily daily;

        if (existingDaily.isPresent()) {
            // 기존 데이터가 있으면 update
            daily = existingDaily.get();

            // null 아닐 때만 수정
            if (request.getDrawingTime() != null) {
                daily.setDrawingTime(request.getDrawingTime());
            }
            if (request.getFeedback() != null) {
                daily.setFeedback(request.getFeedback());
            }
            if (request.getDifficultIssue() != null) {
                daily.setDifficultIssue(request.getDifficultIssue());
            }
            if (request.getGoodIssue() != null) {
                daily.setGoodIssue(request.getGoodIssue());
            }
            if (request.getTodayMood() != null) {
                daily.setTodayMood(request.getTodayMood());
            }
            if (request.getMoodDetail() != null) {
                daily.setMoodDetail(request.getMoodDetail());
            }
            if (request.getQuestion() != null) {
                daily.setQuestion(request.getQuestion());
            }

        } else {
            // 기존 데이터가 없으면 "새로운 데이터 저장" (create)
            daily = Daily.builder()
                    .user(user)
                    .dailyDayRecording(request.getDailyDayRecording())
                    .drawing(request.getDrawing())
                    .drawingTime(request.getDrawingTime())
                    .feedback(request.getFeedback())
                    .difficultIssue(request.getDifficultIssue())
                    .goodIssue(request.getGoodIssue())
                    .todayMood(request.getTodayMood())
                    .moodDetail(request.getMoodDetail())
                    .question(request.getQuestion())
                    .build();
        }

        dailyRepository.save(daily);
        return new DailyResponseDTO(daily);
    }


}
