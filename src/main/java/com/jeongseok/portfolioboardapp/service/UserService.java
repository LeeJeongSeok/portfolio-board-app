package com.jeongseok.portfolioboardapp.service;

import com.jeongseok.portfolioboardapp.domain.User;
import com.jeongseok.portfolioboardapp.dto.user.UserFormDto.UserJoinForm;
import com.jeongseok.portfolioboardapp.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public void join(UserJoinForm userJoinForm) {
		userRepository.save(userJoinForm.toEntity());
	}

	/**
	 * 입력받은 아이디와 데이터베이스에 저장된 아이디가 중복 되는지 확인
	 * false 리턴 시 중복되는 아이디가 없음
	 * true 리턴 시 중복되는 아이디가 존재함
	 */
	public boolean isDuplicate(String userId) {
		Optional<User> findUser = userRepository.findByUserId(userId);

		if (findUser.isEmpty()) {
			return false;
		}

		return findUser.get().getUserId().equals(userId);
	}
}
