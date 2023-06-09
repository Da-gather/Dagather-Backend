package kr.org.dagather.domain.mission_complete.controller;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.domain.mission_complete.dto.*;
import kr.org.dagather.domain.mission_complete.repository.MissionCompleteRepository;
import kr.org.dagather.domain.mission_complete.service.MissionCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/mission")
@RequiredArgsConstructor
public class MissionCompleteController {
    private final MissionCompleteService missionCompleteService;
    private final MissionCompleteRepository missionCompleteRepository;

    @PostMapping("")
    public ApiResponse<MissionCompleteSaveResponseDto> save(@RequestBody MissionCompleteSaveRequestDto requestDto) {
        return ApiResponse.success(SuccessCode.MISSION_CREATE_SUCCESS, missionCompleteService.save(requestDto));
    }

    @RequestMapping(value = "/ongoing", method = RequestMethod.GET)
    public ApiResponse<MissionCompleteResponseDto> getOngoingMission(@RequestParam("memberId1") String memberId1, @RequestParam("memberId2") String memberId2) {
        return ApiResponse.success(SuccessCode.MISSION_READ_SUCCESS, missionCompleteService.findOngoingMission(memberId1, memberId2));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiResponse<List<MissionCompleteProfileResponseDto>> getCompletedMissions(@RequestParam("memberId1") String memberId1, @RequestParam("memberId2") String memberId2) {
         return ApiResponse.success(SuccessCode.MISSION_READ_SUCCESS, missionCompleteService.findByMemberIds(memberId1, memberId2));
    }

    @PatchMapping("")
    public ApiResponse<MissionCompleteUpdateResponseDto> update(@RequestBody MissionCompleteUpdateRequestDto requestDto) {
        return ApiResponse.success(SuccessCode.MISSION_COMPLETE_SUCCESS, missionCompleteService.update(requestDto));
    }

    @RequestMapping(value = "/ongoings", method = RequestMethod.GET)
    public ApiResponse<List<MissionCompleteProfileResponseDto>> getOngoingMissions(@RequestParam("memberId") String memberId) {
        return ApiResponse.success(SuccessCode.MISSION_READ_SUCCESS, missionCompleteService.findOngoingMissions(memberId));
    }

    @RequestMapping(value = "/statistics", method = RequestMethod.GET)
    public ApiResponse<MissionCompleteCountResponseDto> getMissionStatistics(@RequestParam("memberId") String memberId) {
        return ApiResponse.success(SuccessCode.MISSION_READ_SUCCESS, missionCompleteService.getMissionStatistics(memberId));
    }

    @RequestMapping(value = "/recent10", method = RequestMethod.GET)
    public ApiResponse<List<MissionCompleteProfileResponseDto>> getRecent10(@RequestParam("memberId") String memberId) {
        return ApiResponse.success(SuccessCode.MISSION_READ_SUCCESS, missionCompleteService.findRecent10CompleteMissions(memberId));
    }
}
