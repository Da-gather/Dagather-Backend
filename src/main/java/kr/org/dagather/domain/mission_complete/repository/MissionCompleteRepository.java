package kr.org.dagather.domain.mission_complete.repository;

import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MissionCompleteRepository extends JpaRepository<MissionComplete, Long> {
    List<MissionComplete> findByMemberId1AndMemberId2OrderByCompletedAtDesc(Integer memberId1, Integer memberId2);

    @Query(value = "select * from mission_complete where member_id1 = :memberId1 and member_id2 = :memberId2 and complete1 = true and complete2 = true", nativeQuery = true)
    List<MissionComplete> findByMemberIds(@Param("memberId1") Integer memberId1, @Param("memberId2") Integer memberId2);

    @Query(value = "select mission_id from mission_complete where member_id1 = :memberId1 and member_id2 = :memberId2", nativeQuery = true)
    List<Integer> findCompleteMissionIds(@Param("memberId1") Integer memberId1, @Param("memberId2") Integer memberId2);

    @Query(value = "delete from mission_complete where member_id1 = :memberId1 and member_id2 = :memberId2 and (isnull(complete1) or isnull(complete2))", nativeQuery = true)
    void deleteByMemberIds(@Param("memberId1") Integer memberId1, @Param("memberId2") Integer memberId2);
}
