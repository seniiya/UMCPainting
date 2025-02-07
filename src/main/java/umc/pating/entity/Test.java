package umc.pating.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.pating.entity.common.BaseEntity;
import umc.pating.entity.enums.TodayMood;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Test extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate testDayRecording;

    private String title;

    @Lob
    private String drawing;

    private String drawingTime;

    private Integer score;

    @Lob
    private String feedback;

    @Lob
    private String difficultIssue;

    @Lob
    private String goodIssue;

    @Lob
    private String addTime;

    private Integer satisfication; // 시험 만족도

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodayMood todayMood;

    private String moodDetail;

    private String question;
}
