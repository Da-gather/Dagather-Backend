package kr.org.dagather.domain.mission.controller;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.domain.mission.dto.MissionSaveRequestDto;
import kr.org.dagather.domain.mission.entity.Mission;
import kr.org.dagather.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MissionController {
    private final MissionService missionService;

    @PostMapping("/api/v1/mission/add")
    public ApiResponse<Mission> save(@RequestBody MissionSaveRequestDto requestDto) {
        return ApiResponse.success(SuccessCode.MISSION_SAVE_SUCCESS, missionService.save(requestDto));
    }
}
