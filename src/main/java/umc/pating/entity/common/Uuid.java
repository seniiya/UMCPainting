package umc.pating.entity.common;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class Uuid extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    public String randomUuID() {
        return UUID.randomUUID().toString();
    }
}
