package kr.org.dagather.domain.mission_complete.dto;

import kr.org.dagather.domain.mission.entity.Mission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionCompleteUpdateRequestDto {
    private Mission missionId;
    private String memberId1;
    private String memberId2;
    private Boolean complete1;
    private Boolean complete2;

    @Builder
    public MissionCompleteUpdateRequestDto(Boolean complete1, Boolean complete2) {
        this.complete1 = complete1;
        this.complete2 = complete2;
    }
}
