package umc.pating.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.pating.entity.common.BaseEntity;
import umc.pating.entity.enums.StressType;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "mood_record")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor


// 이거 스트레스 기록할 때 쓰는 거임 - 월별 기록에 나오는 mood 아님
public class MoodRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StressType stress;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}
