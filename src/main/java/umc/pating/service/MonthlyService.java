package umc.pating.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.Daily;
import umc.pating.entity.Test;
import umc.pating.repository.DailyRepository;
import umc.pating.repository.TestRepository;
import umc.pating.services.MonthlyResponseDTO;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyService {
    private final DailyRepository dailyRepository;
    private final TestRepository testRepository;

    public List<MonthlyResponseDTO> getMonthly(Long userId, int year, int month) {

        LocalDate startMonth = YearMonth.of(year, month).atDay(1);
        LocalDate endMonth = YearMonth.of(year, month).atEndOfMonth();

        List<Daily> dailyRecords = dailyRepository.findByUserIdAndDailyDayRecordingBetween(userId, startMonth, endMonth);

        List<Test> testRecords = testRepository.findByUserIdAndTestDayRecordingBetween(userId, startMonth, endMonth);

        List<MonthlyResponseDTO> monthlyRecords = new ArrayList<>();

        for (Daily daily : dailyRecords) {
            String displayImage;
            if (daily.getDrawing() != null && !daily.getDrawing().trim().isEmpty()) {
                displayImage = daily.getDrawing();  // 📌 사진이 있으면 사진 먼저
            } else if (daily.getTodayMood() != null) {
                displayImage = daily.getTodayMood().name();  // 📌 Enum 타입일 경우 name()으로 문자열 변환
            } else {
                displayImage = "";
            }

            monthlyRecords.add(new MonthlyResponseDTO(
                    daily.getDailyDayRecording(),
                    displayImage
            ));

        }

        // 🔹 Test 데이터 추가
        for (Test test : testRecords) {
            String displayImage;
            if (test.getDrawing() != null && !test.getDrawing().trim().isEmpty()) {
                displayImage = test.getDrawing();  // 📌 사진이 있으면 사진 먼저
            } else if (test.getTodayMood() != null) {
                displayImage = test.getTodayMood().name();  // 📌 Enum 타입일 경우 name()으로 문자열 변환
            } else {
                displayImage = "NULL_CHECK";
            }

            monthlyRecords.add(new MonthlyResponseDTO(
                    test.getTestDayRecording(),
                    displayImage
            ));

        }

        monthlyRecords.sort(Comparator.comparing(MonthlyResponseDTO::getDate));

        return monthlyRecords;

    }

}