package kr.org.dagather.domain.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@NoArgsConstructor
@Getter
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Getter
	private Long id;

	@Column(name = "member_id", nullable = false)
	private String memberId;

	@Column(nullable = false)
	private float longitude;

	@Column(nullable = false)
	private float latitude;

	@Builder
	Location(String memberId, float longitude, float latitude) {
		Assert.notNull(memberId, "memberId must not be null");
		Assert.notNull(longitude, "memberId must not be null");
		Assert.notNull(latitude, "memberId must not be null");

		this.memberId = memberId;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
