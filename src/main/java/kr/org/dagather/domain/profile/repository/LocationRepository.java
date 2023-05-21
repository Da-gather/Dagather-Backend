package kr.org.dagather.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.org.dagather.domain.profile.entity.Location;
import kr.org.dagather.domain.profile.entity.Profile;

public interface LocationRepository extends JpaRepository<Location, Long> {

	void deleteAllByProfile(Profile profile);
}
