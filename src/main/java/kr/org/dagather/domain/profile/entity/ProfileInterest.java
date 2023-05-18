package kr.org.dagather.domain.profile.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.http.util.Asserts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile_interest")
@Getter
@Setter
@NoArgsConstructor
public class ProfileInterest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Getter
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_id")
	private Profile profile;

	@Column(nullable = false, columnDefinition = "VARCHAR(255) CHARACTER SET UTF8")
	private String interest;

	@Builder
	ProfileInterest(Profile profile, String interest) {
		Asserts.notNull(profile, "profile must not be null");
		Asserts.notNull(interest, "interest must not be null");

		this.profile = profile;
		this.interest = interest;
	}
}
