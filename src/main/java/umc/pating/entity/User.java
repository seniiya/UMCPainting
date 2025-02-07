package umc.pating.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import umc.pating.entity.enums.ExamCategory;
import umc.pating.entity.enums.UserStatus;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "user")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor


public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    public Long getUserId() {
        return id;
    }

    @Column(nullable = false)
    private String userName;

    private String nickname;

//    @Column(nullable = false)
    private Integer birthYear;

    @Enumerated(EnumType.STRING) //유저의 신분
    private UserStatus status;

    @Enumerated(EnumType.STRING) //유저의 입시유형
    private ExamCategory category;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role;

    private String provider;  // ✅ 소셜 로그인 제공자 (e.g., google, facebook, naver)
    private String providerId;  // ✅ 소셜 로그인 제공자의 사용자 ID

    @CreationTimestamp
    private Timestamp timestamp;
}

