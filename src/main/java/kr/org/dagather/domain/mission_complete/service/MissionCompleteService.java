package kr.org.dagather.domain.mission_complete.service;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.domain.mission.entity.Mission;
import kr.org.dagather.domain.mission.repository.MissionRepository;
import kr.org.dagather.domain.mission_complete.dto.MissionCompleteResponseDto;
import kr.org.dagather.domain.mission_complete.dto.MissionCompleteSaveRequestDto;
import kr.org.dagather.domain.mission_complete.dto.MissionCompleteUpdateRequestDto;
import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import kr.org.dagather.domain.mission_complete.repository.MissionCompleteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MissionCompleteService {
    private final MissionRepository missionRepository;
    private final MissionCompleteRepository missionCompleteRepository;

    @Transactional
    public MissionComplete save(MissionCompleteSaveRequestDto requestDto) {

        // 완료하지 않은(건너뛴) 미션 삭제
        missionCompleteRepository.deleteByMemberIds(requestDto.getMemberId1(), requestDto.getMemberId2());

        // 전체 미션 개수와 완료한 미션 개수 비교
        long MissionCount = missionRepository.count();
        List<Integer> CompletedMissionIds = missionCompleteRepository.findCompleteMissionIds(requestDto.getMemberId1(), requestDto.getMemberId2());
        Mission mission = null;
        if (MissionCount == CompletedMissionIds.size()) {
            throw new NullPointerException();
        }
        else {
            while (Boolean.TRUE) {  // 이미 완료한 미션인지 확인 -> 중복 미션 생성 X
                mission = missionRepository.findByRandom();
                if (CompletedMissionIds.contains(mission.getId())) continue;
                else break;
            }
        }
        requestDto.setMissionId(mission);
        return missionCompleteRepository.save(requestDto.toEntity());
    }

//    public MissionCompleteResponseDto findByMemberIds(Integer memberId1, Integer memberId2) {
//    public List<MissionComplete> findByMemberIds(Integer memberId1, Integer memberId2) {
//        List<MissionComplete> entity = missionCompleteRepository.findByMemberIds(memberId1, memberId2);
//        if (entity == null) throw new CustomException(ErrorCode.COMPLETEDMISSION_NOT_FOUND);
//
//        List<MissionCompleteResponseDto> responseDto = new ArrayList<>();
//        for(MissionComplete missionComplete : entity){
//            MissionComplete m = new MissionCompleteResponseDto.builder().data(missionComplete).build();
//        }
//        return new MissionCompleteResponseDto(entity);
//        return missionCompleteRepository.findByMemberIds(memberId1, memberId2);
//    }

    @Transactional
    public String update(MissionCompleteUpdateRequestDto requestDto) {
        List<MissionComplete> missionCompletes
                = missionCompleteRepository.findByMemberId1AndMemberId2OrderByCompletedAtDesc(requestDto.getMemberId1(), requestDto.getMemberId2());
        MissionComplete onGoingMission = missionCompletes.get(0);

        if (requestDto.getComplete1() == null) requestDto.setComplete1(onGoingMission.getComplete1());
        if (requestDto.getComplete2() == null) requestDto.setComplete2(onGoingMission.getComplete2());
        onGoingMission.update(requestDto.getComplete1(), requestDto.getComplete2());
        return "success";
    }
}
