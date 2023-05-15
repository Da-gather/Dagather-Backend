package kr.org.dagather.domain.mission_complete.dto;

import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MissionCompleteResponseDto {
    private String mission;
    private Integer category;
    private String completedAt;

    @Builder
    public MissionCompleteResponseDto(MissionComplete entity) {
        this.mission = entity.getMissionId().getMission();
        this.category = entity.getMissionId().getCategory();
        this.completedAt = entity.getCompletedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
