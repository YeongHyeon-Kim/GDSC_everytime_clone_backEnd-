package seoultech.gdsc.web.user.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seoultech.gdsc.web.entity.User;
import seoultech.gdsc.web.user.dto.*;
import seoultech.gdsc.web.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;


	@Transactional
	public String userJoin(UserDto.SaveRequest user){
		if(userRepository.existsUserByUserId(user.getUserId())){
			return "아이디가 중복 되었습니다";
		}
		if(userRepository.existsUserByEmail(user.getEmail())){
			return "이메일이 중복되었습니다";
		}
		if(userRepository.existsUserByNickname(user.getNickname())){
			return "닉네임이 중복 되었습니다";
		}
		if(userRepository.existsUserByHp(user.getHp())){
			return "전화번호가 중복 되었습니다";
		}

		User newUser = modelMapper.map(user, User.class);
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		newUser.setPassword(encodedPassword);
		userRepository.save(newUser);

		return "";
	}
	@Transactional
	public UserDto.GetRequest getUserInfo(int id){
		User userInfo = userRepository.findById(id).orElseThrow(()->
				new IllegalArgumentException("해당 유저는 없습니다. id = " + id)); // 이거 다른사람들은 어떻게 처리했는지
		return modelMapper.map(userInfo, UserDto.GetRequest.class);

	}

	@Transactional
	public int login(LoginDto loginDto){
		Optional<User> user = userRepository.findByUserId(loginDto.getUserId());
		if (user.isPresent()){
			User getUser = user.get();
			boolean checkPassword = passwordEncoder.matches(loginDto.getPassword(), getUser.getPassword());
			if (checkPassword){
				return getUser.getId();
			}
			else{
				return 0;
			}
		}
		return 0;
	}
	@Transactional
	public Optional<User> changeNick(int id, String nickName){
		if(userRepository.existsUserByNickname(nickName)){
			return Optional.empty();
		}
		User user = userRepository.getById(id);
		user.setNickname(nickName);
		userRepository.save(user);
		return Optional.of(user);
	}
	@Transactional
	public void deleteUser(int id){
		userRepository.deleteById(id);
	}

}
