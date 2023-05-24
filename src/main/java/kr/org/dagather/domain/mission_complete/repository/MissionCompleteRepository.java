package kr.org.dagather.domain.mission_complete.repository;

import kr.org.dagather.domain.mission_complete.entity.MissionComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissionCompleteRepository extends JpaRepository<MissionComplete, Long> {

    @Query(value = "select * from mission_complete where ((member_id1 = :memberId1 and member_id2 = :memberId2) or (member_id1 = :memberId2 and member_id2 = :memberId1)) and complete1 = true and complete2 = true", nativeQuery = true)
    List<MissionComplete> findByMemberIds(@Param("memberId1") String memberId1, @Param("memberId2") String memberId2);

    @Query(value = "select * from mission_complete where ((member_id1 = :memberId1 and member_id2 = :memberId2) or (member_id1 = :memberId2 and member_id2 = :memberId1)) order by completed_at desc limit 1", nativeQuery = true)
    MissionComplete findTop1ByMemberId1AndMemberId2OrderByCompletedAtDesc(String memberId1, String memberId2);

    @Query(value = "select mission_id from mission_complete where ((member_id1 = :memberId1 and member_id2 = :memberId2) or (member_id1 = :memberId2 and member_id2 = :memberId1))", nativeQuery = true)
    List<Integer> findCompleteMissionIds(@Param("memberId1") String memberId1, @Param("memberId2") String memberId2);

    @Query(value = "delete from mission_complete where ((member_id1 = :memberId1 and member_id2 = :memberId2) or (member_id1 = :memberId2 and member_id2 = :memberId1)) and (isnull(complete1) or isnull(complete2))", nativeQuery = true)
    void deleteByMemberIds(@Param("memberId1") String memberId1, @Param("memberId2") String memberId2);

    @Query(value = "select * from mission_complete where (member_id1=:memberId or member_id2=:memberId) and (isnull(complete1) or isnull(complete2))", nativeQuery = true)
    List<MissionComplete> findOngoingMissions(@Param("memberId") String memberId);

    @Query(value = "select category, count(*)\n" +
        "from mission_complete mc join mission m on m.id = mc.mission_id\n" +
        "where (member_id1=:memberId or member_id2=:memberId) and complete1=1 and complete2=1\n" +
        "group by category", nativeQuery = true)
    List<List<Integer>> getMissionCount(@Param("memberId") String memberId);

    @Query(value = "select * from mission_complete where (member_id1=:memberId or member_id2=:memberId) and complete1 = true and complete2 = true limit 10", nativeQuery = true)
    List<MissionComplete> findRecent10CompleteMissions(@Param("memberId") String memberId);
}
