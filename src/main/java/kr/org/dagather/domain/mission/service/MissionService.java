package kr.org.dagather.domain.mission.service;

import kr.org.dagather.domain.mission.dto.MissionSaveRequestDto;
import kr.org.dagather.domain.mission.entity.Mission;
import kr.org.dagather.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class MissionService {
    private final MissionRepository missionRepository;

    @Transactional
    public Mission save(MissionSaveRequestDto requestDto){
        return missionRepository.save(requestDto.toEntity());
    }
}
