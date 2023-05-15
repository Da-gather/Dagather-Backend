package kr.org.dagather.domain.mission_complete.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MissionCompleteSaveResponseDto {
    private String mission;
    private Integer category;
    private Boolean complete1;
    private Boolean complete2;

    @Builder
    public MissionCompleteSaveResponseDto(MissionCompleteSaveRequestDto requestDto) {
        this.mission = requestDto.getMissionId().getMission();
        this.category = requestDto.getMissionId().getCategory();
        this.complete1 = requestDto.getComplete1();
        this.complete1 = requestDto.getComplete2();
    }
}
