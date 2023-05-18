package kr.org.dagather.domain.profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.org.dagather.domain.profile.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

	Optional<Profile> findProfileByMemberId(String memberId);
	Profile findByMemberId(String memberId);
}
