package kr.org.dagather.domain.mission_complete.dto;

import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class MissionCompleteProfileResponseDto {
    private Long id;
    private String friendId;
    private String friendImageUrl;
    private String mission;
    private Integer category;
    private String completedAt;

    @Builder
    public MissionCompleteProfileResponseDto(MissionComplete entity, String friendId, String friendImageUrl, Boolean addCompletedAt) {
        this.id = entity.getId();
        this.friendId = friendId;
        this.friendImageUrl = friendImageUrl;
        this.mission = entity.getMissionId().getMission();
        this.category = entity.getMissionId().getCategory();
        if (addCompletedAt){
            this.completedAt = entity.getCompletedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    }
}
