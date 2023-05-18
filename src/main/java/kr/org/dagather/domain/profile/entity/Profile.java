package kr.org.dagather.domain.profile.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="profile")
@Getter
@Setter
@NoArgsConstructor
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	@Getter
	private Long id;

	@Column(name = "member_id", nullable = false)
	private String memberId;

	@Column(nullable = false)
	private String resident;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String imageUrl;

	@Column(nullable = false)
	private boolean gender;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@Column(name = "birth", nullable = false)
	private LocalDate birth;

	@Column(nullable = false)
	private String nationality;

	@Column(nullable = false)
	private String rperiod;

	@Column(nullable = false)
	private String introduction;

	@Builder
	Profile(String memberId, String resident, String name, String imageUrl, boolean gender, LocalDate birth,
		String nationality, String rperiod, String introduction) {
		Assert.notNull(memberId, "memberId must not be null");
		Assert.notNull(resident, "resident must not be null");
		Assert.notNull(name, "name must not be null");
		Assert.notNull(imageUrl, "imageUrl must not be null");
		Assert.notNull(gender, "gender must not be null");
		Assert.notNull(birth, "birth must not be null");
		Assert.notNull(nationality, "nationality must not be null");
		Assert.notNull(rperiod, "rperiod must not be null");
		Assert.notNull(introduction, "introduction must not be null");

		this.memberId = memberId;
		this.resident = resident;
		this.name = name;
		this.imageUrl = imageUrl;
		this.gender = gender;
		this.birth = birth;
		this.nationality = nationality;
		this.rperiod = rperiod;
		this.introduction = introduction;
	}
}
