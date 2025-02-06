package umc.pating.services;


import lombok.*;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;

@Getter
@Setter
public class DailyRequestDTO {
    private Long userId;
    private LocalDate dailyDayRecording;
    private String drawing;
    private String drawingTime;
    private String feedback;
    private String difficultIssue;
    private String goodIssue;
    private TodayMood todayMood;
    private String moodDetail;
    private String question;

}
