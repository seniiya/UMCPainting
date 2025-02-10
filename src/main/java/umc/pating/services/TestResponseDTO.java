package umc.pating.services;


import lombok.AllArgsConstructor;
import lombok.Getter;
import umc.pating.entity.Daily;
import umc.pating.entity.Test;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TestResponseDTO {

    private Long id;
    private LocalDate testDayRecording;
    private String title;
    private String drawing;
    private String drawingTime;
    private Integer score;
    private String feedback;
    private String difficultIssue;
    private String goodIssue;
    private String addTime;
    private Integer satisfication;
    private TodayMood todayMood;
    private String moodDetail;
    private String question;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TestResponseDTO(Test test) {
        this.id = test.getId();
        this.testDayRecording = test.getTestDayRecording();
        this.title = test.getTitle();
        this.drawing = test.getDrawing();
        this.drawingTime = test.getDrawingTime();
        this.score = test.getScore();
        this.feedback = test.getFeedback();
        this.difficultIssue = test.getDifficultIssue();
        this.goodIssue = test.getGoodIssue();
        this.addTime = test.getAddTime();
        this.satisfication = test.getSatisfication();
        this.todayMood = test.getTodayMood();
        this.moodDetail = test.getMoodDetail();
        this.question = test.getQuestion();
        this.createdAt = test.getCreatedAt();
        this.updatedAt = test.getUpdatedAt();
    }
}
