package umc.pating.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import umc.pating.entity.enums.Category;
import umc.pating.entity.enums.Status;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

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
    private Status status;

    @ElementCollection(fetch = FetchType.LAZY)  // 🔥 여러 개의 카테고리를 저장할 수 있도록 변경
    @Enumerated(EnumType.STRING)
    private List<Category> category;  // ✅ 단일 Category → List<Category>로 변경

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = false)
    private String role;

    private String provider;  // ✅ 소셜 로그인 제공자 (e.g., google, facebook, naver)
    private String providerId;  // ✅ 소셜 로그인 제공자의 사용자 ID

    private String refreshToken;

    @CreationTimestamp
    private Timestamp timestamp;

    //가입 년도
    @Column(nullable = false, updatable = false)
    private Integer joinYear;

    // 가입년도 자동으로 설정하는 생성자
    public static User createUser(String userName, String email, Integer birthYear, String role) {
        return User.builder()
                .userName(userName)
                .email(email)
                .birthYear(birthYear)
                .role(role)
                .joinYear(java.time.LocalDate.now().getYear()) // ✅ 현재 연도를 자동으로 설정
                .build();
    }
}