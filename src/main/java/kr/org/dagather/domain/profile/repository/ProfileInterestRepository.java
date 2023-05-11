package kr.org.dagather.domain.profile.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.entity.ProfileInterest;

public interface ProfileInterestRepository extends JpaRepository<ProfileInterest, Long> {
	List<ProfileInterest> findAllByProfile(Profile profile);
	boolean existsByProfileAndInterest(Profile profile, String interest);
}
