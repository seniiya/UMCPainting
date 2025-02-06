package umc.pating.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.pating.entity.enums.ExamCategory;
import umc.pating.entity.enums.UserStatus;

@Entity
@Getter
@Table(name = "user")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor


public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String userName;

    private String nickname;

    @Column(nullable = false)
    private Integer birthYear;

    @Enumerated(EnumType.STRING) //유저의 신분
    private UserStatus status;

    @Enumerated(EnumType.STRING) //유저의 입시유형
    private ExamCategory category;
}
