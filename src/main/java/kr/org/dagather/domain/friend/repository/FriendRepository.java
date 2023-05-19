package kr.org.dagather.domain.friend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.org.dagather.domain.friend.entity.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	boolean existsBySenderAndReceiver(String sender, String receiver);
	Friend findFriendById(Long id);
	List<Friend> findFriendsBySenderAndAreWeFriendFalse(String sender);
	List<Friend> findFriendsByReceiverAndAreWeFriendFalse(String receiver);
	boolean existsByChatroomId(String chatroomId);

	@Query("select "
		+ "f from "
		+ "Friend f "
		+ "where f.areWeFriend = true "
		+ "and (f.sender = :memberId or f.receiver = :memberId)")
	List<Friend> findFriendsByMemberId(@Param("memberId") String memberId);

	@Query("select f.areWeFriend "
		+ "from Friend f "
		+ "where (f.sender = :me and f.receiver = :you) "
		+ "or (f.sender = :you and f.receiver = :me)")
	Optional<Boolean> areWeFriend(@Param("me") String me, @Param("you") String you);

	@Query("select f "
		+ "from Friend f "
		+ "where (f.sender = :member1 and f.receiver = :member2) "
		+ "or (f.sender = :member2 and f.receiver = :member1)")
	Friend findFriendByMembers(String member1, String member2);
}
