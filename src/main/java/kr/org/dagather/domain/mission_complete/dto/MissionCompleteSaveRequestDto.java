package kr.org.dagather.domain.mission_complete.dto;

import kr.org.dagather.domain.mission.entity.Mission;
import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionCompleteSaveRequestDto {
    private Mission missionId;
    private Integer memberId1;
    private Integer memberId2;
    private Boolean complete1;
    private Boolean complete2;

    @Builder
    public MissionCompleteSaveRequestDto(Mission missionId, Integer memberId1, Integer memberId2, Boolean complete1, Boolean complete2) {
        this.missionId = missionId;
        this.memberId1 = memberId1;
        this.memberId2 = memberId2;
        this.complete1 = complete1;
        this.complete2 = complete2;
    }

    public MissionComplete toEntity() {
        return MissionComplete.builder()
                .missionId(missionId)
                .memberId1(memberId1)
                .memberId2(memberId2)
                .complete1(complete1)
                .complete2(complete2)
                .build();
    }
}
