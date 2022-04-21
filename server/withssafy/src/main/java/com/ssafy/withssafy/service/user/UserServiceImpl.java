package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;

    /**
     * 새로운 사용자를 등록한다.
     * @param userDto
     * @return user
     */
    @Override
    public UserDto insertUser(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        User result = userRepository.save(user);
        return modelMapper.map(result, UserDto.class);
    }

    /**
     * 해당 이메일을 가진 사용자를 삭제한다.
     * @param id
     * @return 해당 이메일을 가진 사용자가 있다면 삭제 후 true, 없다면 false
     */
    @Transactional
    @Override
    public Boolean deleteByUid(Long id){
        if(!userRepository.findById(id).isPresent()) return false;
        userRepository.deleteById(id);
        return true;
    }

    /**
     * 해당 사용자의 아이디를 가진 유저의 비밀번호를 수정한다.
     * @param id
     * @param password
     * @return 해당 사용자 User 정보
     */
    @Transactional
    @Override
    public UserDto updatePasswordByUid(Long id, String password) {
        if(!userRepository.findById(id).isPresent()) return null;
        userRepository.updatePasswordById(id, password);
        User user = userRepository.findById(id).get();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findById(Long u_id) {
        if (!userRepository.findById(u_id).isPresent()) return null;

        User user = userRepository.findById(u_id).get();
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * 해당 아이디를 가진 사용자를 조회한다.
     * @param u_id
     * @return 해당 사용자 User
     */
    @Override
    public UserDto findByUid(String u_id) {
        User user = userRepository.findByUid(u_id);
        return modelMapper.map(user, UserDto.class);
    }

    /**
     * 전체 사용자를 조회한다.
     * @return List<User>
     */
    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }


    /**
     * 비밀번호와 아이디를 통해 사용자를 조회한다.
     * @param u_id
     * @param password
     * @return User
     */
    @Override
    public UserDto login(String u_id, String password) {
        User user = userRepository.login(u_id, password);
        return modelMapper.map(user, UserDto.class);
    }
}
