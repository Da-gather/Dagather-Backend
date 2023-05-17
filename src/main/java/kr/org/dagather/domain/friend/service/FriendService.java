package kr.org.dagather.domain.friend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.org.dagather.common.exception.CustomException;
import kr.org.dagather.common.exception.DuplicateException;
import kr.org.dagather.common.exception.NotFoundException;
import kr.org.dagather.common.exception.NumberFormatException;
import kr.org.dagather.common.filter.AuthFilter;
import kr.org.dagather.common.response.ErrorCode;
import kr.org.dagather.domain.friend.dto.FriendChatroomMapper;
import kr.org.dagather.domain.friend.dto.FriendChatroomRequestDto;
import kr.org.dagather.domain.friend.dto.FriendChatroomResponseDto;
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
	private final FriendChatroomMapper friendChatroomMapper;

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

	@Transactional
	public FriendResponseDto acceptFriend(String friendId) {
		try {
			Long id = Long.parseLong(friendId);
			Friend friend = friendRepository.findFriendById(id);
			if (friend == null) throw new NotFoundException(ErrorCode.FRIEND_NOT_FOUND);

			friend.setAreWeFriend(true);
			return friendMapper.toResponseDto(friend);

		} catch (NumberFormatException e) {
			throw new NumberFormatException(ErrorCode.BAD_PARAMETER_TYPE);
		}
	}

	@Transactional
	public FriendChatroomResponseDto setChatroom(FriendChatroomRequestDto requestDto) {
		Friend friend = friendRepository.findFriendById(requestDto.getFriendId());
		if (friend == null) throw new NotFoundException(ErrorCode.FRIEND_NOT_FOUND);
		if (friendRepository.existsByChatroomId(requestDto.getChatroomId()))
			throw new DuplicateException(ErrorCode.DUPLICATED_CHATROOM);

		friend.setChatroomId(requestDto.getChatroomId());

		return friendChatroomMapper.toResponseDto(friend);
	}

	@Transactional
	public Long rejectFriend(String friendId) {
		try {
			Long id = Long.parseLong(friendId);
			Friend friend = friendRepository.findFriendById(id);
			if (friend == null) throw new NotFoundException(ErrorCode.FRIEND_NOT_FOUND);

			friendRepository.delete(friend);
			return friend.getId();
		} catch (NumberFormatException e) {
			throw new NumberFormatException(ErrorCode.BAD_PARAMETER_TYPE);
		}
	}

	@Transactional
	public List<FriendResponseDto> getRequestList(String requestBy) {
		String memberId = AuthFilter.getCurrentMemberId();
		if (memberId == null || memberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		List<Friend> friends;
		if ("1".equals(requestBy)) {
			friends = friendRepository.findFriendsBySenderAndAreWeFriendFalse(memberId);
		} else if ("0".equals(requestBy)) {
			friends = friendRepository.findFriendsByReceiverAndAreWeFriendFalse(memberId);
		} else {
			throw new CustomException(ErrorCode.BAD_PARAMETER);
		}
		List<FriendResponseDto> result = friends.stream()
			.map(friendMapper::toResponseDto).collect(Collectors.toList());

		return result;
	}

	@Transactional
	public List<FriendChatroomResponseDto> getFriendList() {
		String memberId = AuthFilter.getCurrentMemberId();
		if (memberId == null || memberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		List<Friend> friends = friendRepository.findFriendsByMemberId(memberId);
		List<FriendChatroomResponseDto> result = friends.stream()
			.map(friendChatroomMapper::toResponseDto).collect(Collectors.toList());

		return result;
	}
}
