package umc.pating.services;

import lombok.*;
import org.springframework.cglib.core.Local;
import umc.pating.entity.Daily;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DailyResponseDTO {
    private Long id;
    private LocalDate dailyDayRecording;
    private String drawing;
    private String drawingTime;
    private String feedback;
    private String difficultIssue;
    private String goodIssue;
    private TodayMood todayMood;
    private String moodDetail;
    private String question;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DailyResponseDTO(Daily daily) {
        this.id = daily.getId();
        this.dailyDayRecording = daily.getDailyDayRecording();
        this.drawing = daily.getDrawing();
        this.drawingTime = daily.getDrawingTime();
        this.feedback = daily.getFeedback();
        this.difficultIssue = daily.getDifficultIssue();
        this.goodIssue = daily.getGoodIssue();
        this.todayMood = daily.getTodayMood();
        this.moodDetail = daily.getMoodDetail();
        this.question = daily.getQuestion();
        this.createdAt = daily.getCreatedAt();
        this.updatedAt = daily.getUpdatedAt();
    }
}
