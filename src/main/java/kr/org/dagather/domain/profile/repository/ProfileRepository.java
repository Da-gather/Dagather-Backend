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

}
