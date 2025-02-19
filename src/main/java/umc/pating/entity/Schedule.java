package umc.pating.entity;

import jakarta.persistence.*;
import lombok.*;
import umc.pating.entity.common.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer year;


}
