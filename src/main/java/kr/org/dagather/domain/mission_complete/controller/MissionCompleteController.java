package kr.org.dagather.domain.mission_complete.controller;

import kr.org.dagather.common.response.ApiResponse;
import kr.org.dagather.common.response.SuccessCode;
import kr.org.dagather.domain.mission_complete.dto.MissionCompleteResponseDto;
import kr.org.dagather.domain.mission_complete.dto.MissionCompleteSaveRequestDto;
import kr.org.dagather.domain.mission_complete.dto.MissionCompleteUpdateRequestDto;
import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
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
    public ApiResponse<MissionComplete> save(@RequestBody MissionCompleteSaveRequestDto requestDto) {
        return ApiResponse.success(SuccessCode.MISSION_CREATE_SUCCESS, missionCompleteService.save(requestDto));
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<MissionComplete> findByMemberIds(@RequestParam("memberId1") Integer memberId1, @RequestParam("memberId2") Integer memberId2) {
        return missionCompleteRepository.findByMemberIds(memberId1, memberId2);
    }

//    @RequestMapping(value = "", method = RequestMethod.GET)
//    public MissionCompleteResponseDto findByMemberIds(@RequestParam("memberId1") Integer memberId1, @RequestParam("memberId2") Integer memberId2) {
//        return missionCompleteService.findByMemberIds(memberId1, memberId2);
//    }

    @PatchMapping("")
    public String update(@RequestBody MissionCompleteUpdateRequestDto requestDto) {
        return missionCompleteService.update(requestDto);
    }

}
