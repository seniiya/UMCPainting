package umc.pating.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.pating.entity.Daily;
import umc.pating.entity.Test;
import umc.pating.repository.DailyRepository;
import umc.pating.repository.TestRepository;
import umc.pating.repository.UserRepository;
import umc.pating.services.MonthlyResponseDTO;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MonthlyService {
    private final DailyRepository dailyRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    public List<MonthlyResponseDTO> getMonthly(Long userId, int year, int month) {

        LocalDate startMonth = YearMonth.of(year, month).atDay(1);
        LocalDate endMonth = YearMonth.of(year, month).atEndOfMonth();

        List<Daily> dailyRecords = dailyRepository.findByUserIdAndDailyDayRecordingBetween(userId, startMonth, endMonth);

        List<Test> testRecords = testRepository.findByUserIdAndTestDayRecordingBetween(userId, startMonth, endMonth);

        List<MonthlyResponseDTO> monthlyRecords = new ArrayList<>();

        for (Daily daily : dailyRecords) {
            String displayImage = (daily.getDrawing() != null && !daily.getDrawing().isEmpty())
                    ? daily.getDrawing()  // 📌 사진이 있으면 사진 먼저
                    : String.valueOf(daily.getTodayMood()); // 📌 없으면 기분 이모지 표시

            monthlyRecords.add(new MonthlyResponseDTO(
                    daily.getDailyDayRecording(),
                    displayImage
            ));
        }

        // 🔹 Test 데이터 추가
        for (Test test : testRecords) {
            String displayImage = (test.getDrawing() != null && !test.getDrawing().isEmpty())
                    ? test.getDrawing()  // 📌 사진이 있으면 사진 먼저
                    : String.valueOf(test.getTodayMood()); // 📌 없으면 기분 이모지 표시

            monthlyRecords.add(new MonthlyResponseDTO(
                    test.getTestDayRecording(),
                    displayImage
            ));
        }

        monthlyRecords.sort(Comparator.comparing(MonthlyResponseDTO::getDate));

        return monthlyRecords;

    }

}
