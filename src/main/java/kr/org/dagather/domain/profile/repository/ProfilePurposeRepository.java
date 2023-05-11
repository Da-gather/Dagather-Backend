package kr.org.dagather.domain.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.entity.ProfilePurpose;

public interface ProfilePurposeRepository extends JpaRepository<ProfilePurpose, Long> {

	List<ProfilePurpose> findAllByProfile(Profile profile);
	boolean existsByProfileAndPurpose(Profile profile, String purpose);
}
