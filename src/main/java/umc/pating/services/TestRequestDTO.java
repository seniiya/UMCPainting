package umc.pating.services;


import lombok.Getter;
import lombok.Setter;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;

@Getter
@Setter
public class TestRequestDTO {
    private Long userId;
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
}
