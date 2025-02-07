package umc.pating.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import umc.pating.entity.common.BaseEntity;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "daily")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Daily extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daily_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDate dailyDayRecording; // 선택 날짜

    private String drawing; // 그림 (이미지 or 파일 경로)

    private String drawingTime; //소요시간

    @Lob
    private String feedback;

    @Lob
    private String difficultIssue;

    @Lob
    private String goodIssue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true) //
    private TodayMood todayMood;

    private String moodDetail;

    private String question;

    // baseenetity 상속으로 createdat, updatedAt 없음
}
