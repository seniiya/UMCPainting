package umc.pating.services;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // 기본 생성자 추가!
@AllArgsConstructor
public class DailyRequestDTO {
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dailyDayRecording;
    private MultipartFile drawing;
    private String drawingTime;
    private String feedback;
    private String difficultIssue;
    private String goodIssue;
    private TodayMood todayMood;
    private String moodDetail;
    private String question;

}
