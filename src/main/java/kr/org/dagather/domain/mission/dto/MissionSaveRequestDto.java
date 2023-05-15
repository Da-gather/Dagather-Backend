package kr.org.dagather.domain.mission.dto;

import kr.org.dagather.domain.mission.entity.Mission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissionSaveRequestDto {
    private String mission;
    private Integer category;

    @Builder
    public MissionSaveRequestDto(String mission, Integer category) {
        this.mission = mission;
        this.category = category;
    }

    public Mission toEntity() {
        return Mission.builder()
                .mission(mission)
                .category(category)
                .build();
    }
}
