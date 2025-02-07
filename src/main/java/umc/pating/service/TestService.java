package umc.pating.service;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.Test;
import umc.pating.entity.User;
import umc.pating.repository.TestRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.TestRequestDTO;
import umc.pating.services.TestResponseDTO;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    // 현재 로그인한 유저 가져오기
    private User getCurrentUser(Long userId){
        //이거 시큐리티로 로그인한 유저 이메일로 찾기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName(); // 기본적으로 username(email) 반환됨
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("인증된 유저 정보를 찾을 수 없습니다."));
    }

    //특정날짜조회
    @Transactional(readOnly = true)
    public TestResponseDTO getTest(Long userId, LocalDate date) {
        Test test = testRepository.findByUserIdAndTestDayRecording(userId, date)
                .orElseThrow(() -> new RuntimeException("해당 날짜의 기록이 없습니다."));
        return new TestResponseDTO(test);
    }

    // Test 기록 저장/수정
    public TestResponseDTO saveTest(Long userId, TestRequestDTO request) {
        User user = getCurrentUser(userId); // 로그인된 유저 정보 가져오기


        Optional<Test> existingTest = testRepository.findByUserIdAndDate(user.getId(), request.getTestDayRecording());


        Test test;

        if (existingTest.isPresent()) {
            // 기존 데이터가 있으면 update
            test = existingTest.get();

            // null 아닐 때만 수정
            if (request.getDrawingTime() != null) {
                test.setDrawingTime(request.getDrawingTime());
            }
            if (request.getScore() != null) {
                test.setScore(request.getScore());
            }
            if (request.getFeedback() != null) {
                test.setFeedback(request.getFeedback());
            }
            if (request.getDifficultIssue() != null) {
                test.setDifficultIssue(request.getDifficultIssue());
            }
            if (request.getGoodIssue() != null) {
                test.setGoodIssue(request.getGoodIssue());
            }
            if (request.getAddTime() != null) {
                test.setAddTime(request.getAddTime());
            }
            if (request.getSatisfication() != null) {
                test.setSatisfication(request.getSatisfication());
            }
            if (request.getTodayMood() != null) {
                test.setTodayMood(request.getTodayMood());
            }
            if (request.getMoodDetail() != null) {
                test.setMoodDetail(request.getMoodDetail());
            }
            if (request.getQuestion() != null) {
                test.setQuestion(request.getQuestion());
            }

        } else {
            // 기존 데이터가 없으면 "새로운 데이터 저장" (create)
            test = Test.builder()
                    .user(user)
                    .testDayRecording(request.getTestDayRecording())
                    .drawing(request.getDrawing())
                    .drawingTime(request.getDrawingTime())
                    .score(request.getScore())
                    .feedback(request.getFeedback())
                    .difficultIssue(request.getDifficultIssue())
                    .goodIssue(request.getGoodIssue())
                    .addTime(request.getAddTime())
                    .moodDetail(request.getMoodDetail())
                    .todayMood(request.getTodayMood())
                    .moodDetail(request.getMoodDetail())
                    .question(request.getQuestion())
                    .build();
        }

        testRepository.save(test);
        return new TestResponseDTO(test);
    }
}
