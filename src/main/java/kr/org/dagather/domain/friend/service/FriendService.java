package kr.org.dagather.domain.friend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.domain.friend.dto.FriendMapper;
import kr.org.dagather.domain.friend.dto.FriendRequestDto;
import kr.org.dagather.domain.friend.dto.FriendResponseDto;
import kr.org.dagather.domain.friend.entity.Friend;
import kr.org.dagather.domain.friend.repository.FriendRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final FriendMapper friendMapper;

	@Transactional
	public FriendResponseDto requestFriend(FriendRequestDto requestDto) {
		if (friendRepository.existsBySenderAndReceiver(requestDto.getSender(), requestDto.getReceiver())) {
			throw new CustomException(ErrorCode.DUPLICATED_FRIEND);
		}
		Friend friend = friendRepository.save(Friend.builder()
			.sender(requestDto.getSender())
			.receiver(requestDto.getReceiver())
			.build());

		return friendMapper.toResponseDto(friend);
	}
}
