package kr.org.dagather.domain.mission.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String mission;

    @Column(nullable = false)
    private Integer category;

    @Builder
    public Mission(String mission, Integer category) {
        this.mission = mission;
        this.category = category;
    }
}
