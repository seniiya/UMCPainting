package umc.pating.service;


import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.aws.s3.AmazonS3Manager;
import umc.pating.entity.Daily;
import umc.pating.entity.User;
import umc.pating.repository.DailyRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.DailyRequestDTO;
import umc.pating.services.DailyResponseDTO;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DailyService {
    private final DailyRepository dailyRepository;
    private final UserRepository userRepository;
    private final AmazonS3Manager amazonS3Manager;

    // daily 기록 조회
    @Transactional(readOnly = true)
    public DailyResponseDTO getDaily(Long userId, LocalDate date) {
        System.out.println("✅ [DailyService] getDaily 실행 - userId: " + userId + ", date: " + date);

        // 기존 기록 조회
        Optional<Daily> dailyOptional = dailyRepository.findByUserIdAndDailyDayRecording(userId, date);

        Daily daily;

        if (dailyOptional.isPresent()) {
            daily = dailyOptional.get();
            System.out.println("✅ 기존 Daily 기록 조회 완료: " + daily);
        } else {
            System.out.println("⚠️ 기록이 없으므로 새로 생성합니다.");

            // ✅ 유저 정보 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // ✅ 새로운 Daily 기록 생성
            daily = Daily.builder()
                    .user(user)
                    .dailyDayRecording(date)
                    .drawing("")  // 기본값 (초기에는 이미지 없음)
                    .drawingTime("0시간 0분")  // 기본값
                    .feedback("")
                    .difficultIssue("")
                    .goodIssue("")
                    .todayMood(null)
                    .moodDetail("")
                    .question("")
                    .build();

            // 새 기록 저장
            dailyRepository.save(daily);
            System.out.println("✅ 새로운 Daily 기록 생성 완료: " + daily);
        }

        return new DailyResponseDTO(daily);
    }


    // Daily 기록 작성 + 수정
    public DailyResponseDTO saveDaily(Long userId, DailyRequestDTO request) throws IOException {
        System.out.println("🔍 userId from request: " + request.getUserId()); // 디버깅 로그 추가
        System.out.println("🔍 userId from parameter: " + userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Optional<Daily> existingDaily = dailyRepository.findByUserIdAndDailyDayRecording(user.getId(), request.getDailyDayRecording());


        Daily daily;

        // S3에 이미지 업로드 (파일이 있으면)

//        String drawingUrl = null;
//        if (request.getDrawing() != null) {
//            drawingUrl = amazonS3Manager.uploadFile(request.getDrawing()); // S3 업로드 후 URL 반환
//            request.setDrawing(drawingUrl);
//        }


        if (existingDaily.isPresent()) {
            // 기존 데이터가 있으면 update
            daily = existingDaily.get();

            if (request.getDrawing() != null) {
                daily.setDrawing(request.getDrawing());  // 🔹 DB에 S3 URL 저장

            }
//            if (drawingUrl != null) {
//                daily.setDrawing(drawingUrl);
//            }
            if (request.getDrawingTime() != null) {
                daily.setDrawingTime(request.getDrawingTime());
            }
            if (request.getFeedback() != null && daily.getFeedback().trim().isEmpty()) {
                daily.setFeedback(request.getFeedback());
            }
            if (request.getDifficultIssue() != null && daily.getDifficultIssue().trim().isEmpty()) {
                daily.setDifficultIssue(request.getDifficultIssue());
            }
            if (request.getGoodIssue() != null && daily.getGoodIssue().trim().isEmpty()) {
                daily.setGoodIssue(request.getGoodIssue());
            }
            if (request.getTodayMood() != null) {
                daily.setTodayMood(request.getTodayMood());
            }
            if (request.getMoodDetail() != null && daily.getMoodDetail().trim().isEmpty()) {
                daily.setMoodDetail(request.getMoodDetail());
            }
            if (request.getQuestion() != null && daily.getQuestion().trim().isEmpty()) {
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

        System.out.println("📝 저장할 Daily 객체: " + daily);
        dailyRepository.save(daily);
        System.out.println("✅ 저장 완료! id: " + daily.getId());

        return new DailyResponseDTO(daily);
    }


}