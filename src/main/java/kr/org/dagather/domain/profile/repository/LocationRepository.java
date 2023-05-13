package kr.org.dagather.domain.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.org.dagather.domain.profile.entity.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
