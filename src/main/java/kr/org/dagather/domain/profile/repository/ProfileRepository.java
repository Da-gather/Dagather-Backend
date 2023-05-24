package kr.org.dagather.domain.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.org.dagather.domain.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findProfileByMemberId(String memberId);
	Profile findByMemberId(String memberId);
	Profile findProfileById(Long id);
	List<Profile> findAllByNationality(String nationality);

	@Query(value = "select id, gender, image_url, introduction, member_id, `name`, nationality, resident, rperiod, birth, interest, purpose\n"
		+ "from (\n"
		+ "    select p.*, f.id friend_id\n"
		+ "    from profile p left join (\n"
		+ "        select *\n"
		+ "        from friend\n"
		+ "        where sender=:memberId and are_we_friend=true\n"
		+ "    ) f\n"
		+ "    on p.member_id = f.sender\n"
		+ "    intersect\n"
		+ "       select p.*, f.id friend_id\n"
		+ "       from profile p left join (\n"
		+ "           select *\n"
		+ "           from friend\n"
		+ "           where receiver=:memberId and are_we_friend=true\n"
		+ "       ) f\n"
		+ "       on p.member_id = f.receiver\n"
		+ "    ) t;"
		, nativeQuery = true)
	List<Profile> findAllExceptFriend(@Param("memberId") String memberId);
}
