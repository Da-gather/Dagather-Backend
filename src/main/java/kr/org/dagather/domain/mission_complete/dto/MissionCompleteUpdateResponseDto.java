package kr.org.dagather.domain.mission_complete.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class MissionCompleteUpdateResponseDto {
    private String mission;
    private Integer category;
    private Boolean userCompleted;
    private Boolean friendCompleted;

    @Builder
    public MissionCompleteUpdateResponseDto(MissionCompleteUpdateRequestDto requestDto, Boolean Member1isUser) {
        this.mission = requestDto.getMissionId().getMission();
        this.category = requestDto.getMissionId().getCategory();
        if (Member1isUser) {
            this.userCompleted = requestDto.getComplete1();
            this.friendCompleted = requestDto.getComplete2();
        } else {
            this.userCompleted = requestDto.getComplete2();
            this.friendCompleted = requestDto.getComplete1();
        }

    }
}
