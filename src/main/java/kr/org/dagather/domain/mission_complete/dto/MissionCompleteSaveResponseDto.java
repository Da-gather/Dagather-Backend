package kr.org.dagather.domain.mission_complete.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MissionCompleteSaveResponseDto {
    private Long id;
    private String mission;
    private Integer category;
    private Boolean complete1;
    private Boolean complete2;

    @Builder
    public MissionCompleteSaveResponseDto(Long id, MissionCompleteSaveRequestDto requestDto) {
        this.id = id;
        this.mission = requestDto.getMissionId().getMission();
        this.category = requestDto.getMissionId().getCategory();
        this.complete1 = requestDto.getComplete1();
        this.complete2 = requestDto.getComplete2();
    }
}
