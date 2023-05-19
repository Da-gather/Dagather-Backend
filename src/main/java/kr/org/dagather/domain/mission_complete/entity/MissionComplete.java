package kr.org.dagather.domain.mission_complete.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import kr.org.dagather.domain.mission.entity.Mission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class MissionComplete extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "missionId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Mission missionId;

    @Column(nullable = false, updatable = false)
    private String memberId1;

    @Column(nullable = false, updatable = false)
    private String memberId2;

    @Column(columnDefinition = "boolean default false")
    private Boolean complete1;

    @Column(columnDefinition = "boolean default false")
    private Boolean complete2;

    @Builder
    public MissionComplete(Mission missionId, String memberId1, String memberId2, Boolean complete1, Boolean complete2) {
        this.missionId = missionId;
        this.memberId1 = memberId1;
        this.memberId2 = memberId2;
        this.complete1 = complete1;
        this.complete2 = complete2;
    }

    public void update(Boolean complete1, Boolean complete2) {
        this.complete1 = complete1;
        this.complete2 = complete2;
    }
}
