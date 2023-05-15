package kr.org.dagather.domain.friend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.org.dagather.domain.friend.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	boolean existsBySenderAndReceiver(String sender, String receiver);
}
