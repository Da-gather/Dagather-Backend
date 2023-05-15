package kr.org.dagather.domain.friend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="friend")
@Getter
@Setter
@NoArgsConstructor
public class Friend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column
	private String chatroomId;

	@Column(nullable = false)
	private String sender;

	@Column(nullable = false)
	private String receiver;

	@Column(nullable = false)
	private boolean areWeFriend;

	@Builder
	Friend(String sender, String receiver) {
		Assert.notNull(sender, "sender must not be null");
		Assert.notNull(receiver, "sender must not be null");

		this.sender = sender;
		this.receiver = receiver;
		this.areWeFriend = false;
	}

}
