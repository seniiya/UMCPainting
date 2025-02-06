package umc.pating.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "daily_comment")
public class DailyComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "daily_id", nullable = false) // null이어도 되지 않나
    private Daily daily;

    private String title;

    @Lob
    private String content;

    // 사진에서의 x,y 좌표 (픽셀값)
    private int x;
    private int y;
}
