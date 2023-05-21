package kr.org.dagather.domain.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@Column(nullable = false)
	private float longitude;

	@Column(nullable = false)
	private float latitude;

	@Builder
	Location(Profile profile, float longitude, float latitude) {
		Assert.notNull(profile, "profile must not be null");
		Assert.notNull(longitude, "longitude must not be null");
		Assert.notNull(latitude, "latitude must not be null");

		this.profile = profile;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
