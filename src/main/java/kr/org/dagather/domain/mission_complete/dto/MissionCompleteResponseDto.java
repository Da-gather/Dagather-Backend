package kr.org.dagather.domain.mission_complete.dto;

import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import kr.org.dagather.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MissionCompleteResponseDto {
    private Long id;

    private String userId;
    private String userName;
    private String userImageUrl;

    private String friendId;
    private String friendName;
    private String friendImageUrl;

    private String mission;
    private Integer category;
    private Boolean complete1;
    private Boolean complete2;

    @Builder
    public MissionCompleteResponseDto(MissionComplete entity, Profile userProfile, Profile friendProfile) {
        this.id = entity.getId();

        this.userId = userProfile.getMemberId();
        this.userName = userProfile.getName();
        this.userImageUrl = userProfile.getImageUrl();

        this.friendId = friendProfile.getMemberId();
        this.friendName = friendProfile.getName();
        this.friendImageUrl = friendProfile.getImageUrl();

        this.mission = entity.getMissionId().getMission();
        this.category = entity.getMissionId().getCategory();
        this.complete1 = entity.getComplete1();
        this.complete2 = entity.getComplete2();
    }
}
