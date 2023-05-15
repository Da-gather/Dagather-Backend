package kr.org.dagather.domain.mission_complete.dto;

import kr.org.dagather.domain.mission.entity.Mission;
import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MissionCompleteResponseDto {
    private Long id;
    private Mission missionId;
    private Integer memberId1;
    private Integer memberId2;
    private LocalDateTime completedAt;

    @Builder
    public MissionCompleteResponseDto(MissionComplete entity) {
        this.id = entity.getId();
        this.missionId = entity.getMissionId();
        this.memberId1 = entity.getMemberId1();
        this.memberId2 = entity.getMemberId2();
        this.completedAt = entity.getCompletedAt();
    }
}
