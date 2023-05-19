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
import kr.org.dagather.domain.friend.dto.FriendChatroomRequestDto;
import kr.org.dagather.domain.friend.dto.FriendMapper;
import kr.org.dagather.domain.friend.dto.FriendRequestDto;
import kr.org.dagather.domain.friend.dto.FriendListResponseDto;
import kr.org.dagather.domain.friend.dto.FriendResponseDto;
import kr.org.dagather.domain.friend.entity.Friend;
import kr.org.dagather.domain.friend.repository.FriendRepository;
import kr.org.dagather.domain.profile.entity.Profile;
import kr.org.dagather.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;
	private final ProfileRepository profileRepository;
	private final FriendMapper friendMapper;

	@Transactional
	public FriendResponseDto requestFriend(FriendRequestDto requestDto) {
		String memberId = AuthFilter.getCurrentMemberId();
		if (friendRepository.existsBySenderAndReceiver(memberId, requestDto.getReceiver())) {
			throw new CustomException(ErrorCode.DUPLICATED_FRIEND);
		}
		Friend friend = friendRepository.save(Friend.builder()
			.sender(memberId)
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
	public FriendResponseDto setChatroom(FriendChatroomRequestDto requestDto) {
		Friend friend = friendRepository.findFriendById(requestDto.getFriendId());
		if (friend == null) throw new NotFoundException(ErrorCode.FRIEND_NOT_FOUND);
		if (friendRepository.existsByChatroomId(requestDto.getChatroomId()))
			throw new DuplicateException(ErrorCode.DUPLICATED_CHATROOM);

		friend.setChatroomId(requestDto.getChatroomId());

		return friendMapper.toResponseDto(friend);
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
	public List<FriendListResponseDto> getRequestList(String requestBy) {
		String memberId = AuthFilter.getCurrentMemberId();
		if (memberId == null || memberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		List<Friend> friends;
		List<FriendListResponseDto> result;
		if ("1".equals(requestBy)) {
			friends = friendRepository.findFriendsBySenderAndAreWeFriendFalse(memberId);
			result = friends.stream().map(friend -> {
				Profile profile = profileRepository.findByMemberId(friend.getReceiver());
				return friendMapper.toResponseDto(friend, profile);
			}).collect(Collectors.toList());

		} else if ("0".equals(requestBy)) {
			friends = friendRepository.findFriendsByReceiverAndAreWeFriendFalse(memberId);
			result = friends.stream().map(friend -> {
				Profile profile = profileRepository.findByMemberId(friend.getSender());
				return friendMapper.toResponseDto(friend, profile);
			}).collect(Collectors.toList());
		} else {
			throw new CustomException(ErrorCode.BAD_PARAMETER);
		}

		return result;
	}

	@Transactional
	public List<FriendListResponseDto> getFriendList() {
		String memberId = AuthFilter.getCurrentMemberId();
		if (memberId == null || memberId.isEmpty()) throw new CustomException(ErrorCode.NO_ID);

		List<Friend> friends = friendRepository.findFriendsByMemberId(memberId);
		return friends.stream()
			.map(friend -> {
				Profile profile;
				if (friend.getSender().equals(memberId)) {
					profile = profileRepository.findByMemberId(friend.getReceiver());
				} else {
					profile = profileRepository.findByMemberId(friend.getSender());
				}
				return friendMapper.toResponseDto(friend, profile);
			}).collect(Collectors.toList());
	}
}
