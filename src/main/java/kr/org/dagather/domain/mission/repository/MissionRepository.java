package kr.org.dagather.domain.mission.repository;

import kr.org.dagather.domain.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface MissionRepository extends JpaRepository<Mission, Integer> {
    @Query(value = "select * from mission order by rand() limit 1;", nativeQuery = true)
    Mission findByRandom();
}
