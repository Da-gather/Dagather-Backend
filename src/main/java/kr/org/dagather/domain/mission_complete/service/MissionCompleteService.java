package kr.org.dagather.domain.mission_complete.service;

import kr.org.dagather.domain.mission.entity.Mission;
import kr.org.dagather.domain.mission.repository.MissionRepository;
import kr.org.dagather.domain.mission_complete.dto.*;
import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import kr.org.dagather.domain.mission_complete.repository.MissionCompleteRepository;
import kr.org.dagather.domain.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissionCompleteService {
    private final MissionRepository missionRepository;
    private final MissionCompleteRepository missionCompleteRepository;
    private final ProfileService profileService;

    @Transactional
    public MissionCompleteSaveResponseDto save(MissionCompleteSaveRequestDto requestDto) {

        // isAllMissionCompleted
        long WholeMissionCount = missionRepository.count();
        List<Integer> CompletedMissionIds = missionCompleteRepository.findCompleteMissionIds(requestDto.getMemberId1(), requestDto.getMemberId2());
        Mission mission = null;

        // Delete skipped mission
        missionCompleteRepository.deleteByMemberIds(requestDto.getMemberId1(), requestDto.getMemberId2());

        // 이미 완료한 미션인지 확인 -> 중복 미션 생성 X
        // TODO : 모든 미션을 완료한 경우 처리
        if (WholeMissionCount >  CompletedMissionIds.size()) {
            do {
                mission = missionRepository.findByRandom();
            } while (CompletedMissionIds.contains(mission.getId()));
        }

        // save request in entity
        requestDto.setMissionId(mission);
        missionCompleteRepository.save(requestDto.toEntity());

        // return response
        return new MissionCompleteSaveResponseDto(requestDto);
    }
    
    public List<MissionCompleteResponseDto> findByMemberIds(String memberId1, String memberId2) {

        // TODO : 완료한 미션이 없는 경우 처리
        List<MissionComplete> entity = missionCompleteRepository.findByMemberIds(memberId1, memberId2);
        List<MissionCompleteResponseDto> responseDto = new ArrayList<>();
        for(MissionComplete missionComplete : entity){
             responseDto.add(new MissionCompleteResponseDto(missionComplete));
        }
        return responseDto;
    }

    public List<MissionCompleteProfileResponseDto> findOngoingMissions(String memberId) {
        List<MissionComplete> entity = missionCompleteRepository.findOngoingMissions(memberId);
        List<MissionCompleteProfileResponseDto> responseDto = new ArrayList<>();
        String friendId;
        for(MissionComplete missionComplete : entity) {
            String imageUrl = profileService.getProfile(memberId, memberId).getImageUrl();
            if (Objects.equals(missionComplete.getMemberId1(), memberId)) {
                friendId = missionComplete.getMemberId2();

            } else {
                friendId = missionComplete.getMemberId1();
            }
            responseDto.add(new MissionCompleteProfileResponseDto(missionComplete, friendId, imageUrl, Boolean.FALSE));
        }
        return responseDto;
    }

    public MissionCompleteCountResponseDto getMissionStatistics(String memberId) {
        List<List<Integer>> entity = missionCompleteRepository.getMissionCount(memberId);
        MissionCompleteCountResponseDto responseDto = new MissionCompleteCountResponseDto(entity);
        return responseDto;
    }

    public List<MissionCompleteProfileResponseDto> findRecent10CompleteMissions(String memberId) {
        List<MissionComplete> entity = missionCompleteRepository.findRecent10CompleteMissions(memberId);
        List<MissionCompleteProfileResponseDto> responseDto = new ArrayList<>();
        String friendId;
        for(MissionComplete missionComplete : entity) {
            String imageUrl = profileService.getProfile(memberId, memberId).getImageUrl();
            if (Objects.equals(missionComplete.getMemberId1(), memberId)) {
                friendId = missionComplete.getMemberId2();

            } else {
                friendId = missionComplete.getMemberId1();
            }
            responseDto.add(new MissionCompleteProfileResponseDto(missionComplete, friendId, imageUrl, Boolean.TRUE));
        }
        return responseDto;
    }

    @Transactional
    public MissionCompleteUpdateResponseDto update(MissionCompleteUpdateRequestDto requestDto) {
        
        // update entity
        List<MissionComplete> missionCompletes = missionCompleteRepository.findByMemberId1AndMemberId2OrderByCompletedAtDesc(requestDto.getMemberId1(), requestDto.getMemberId2());
        MissionComplete onGoingMission = missionCompletes.get(0);
        if (requestDto.getComplete1() == null) requestDto.setComplete1(onGoingMission.getComplete1());
        if (requestDto.getComplete2() == null) requestDto.setComplete2(onGoingMission.getComplete2());
        onGoingMission.update(requestDto.getComplete1(), requestDto.getComplete2());

        // return response
        requestDto.setMissionId(onGoingMission.getMissionId());
        return new MissionCompleteUpdateResponseDto(requestDto);
    }
}
