package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.dto.classroom.ClassRoomDto;
import com.ssafy.withssafy.dto.manager.ManagerDto;
import com.ssafy.withssafy.dto.user.LoginDto;
import com.ssafy.withssafy.dto.user.UserDto;
import com.ssafy.withssafy.entity.ClassManager;
import com.ssafy.withssafy.entity.ClassRoom;
import com.ssafy.withssafy.entity.Manager;
import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.errorcode.ErrorCode;
import com.ssafy.withssafy.exception.InvalidRequestException;
import com.ssafy.withssafy.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private ClassManagerRepository classManagerRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * 새로운 사용자를 등록한다.
     * @param userDto
     * @return user
     */
    @Override
    public UserDto insertUser(UserDto userDto){
        if(userRepository.findByUid(userDto.getUserId()).isPresent()){
            throw new InvalidRequestException(ErrorCode.JOINED_USER_ID);
        }
        
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
     * @param userId
     * @return 해당 사용자 User
     */
    @Override
    public UserDto findByUid(String userId) {

        User user = userRepository.findByUid(userId).get();
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
     * @param userId
     * @param password
     * @return User
     */
    @Override
    public LoginDto login(String userId, String password) {
        User user = userRepository.login(userId, password);
        LoginDto result = modelMapper.map(user, LoginDto.class);
        result.setClassRoomDto(modelMapper.map(user.getClassRoom(), ClassRoomDto.class));
        Manager manager = managerRepository.findByUId(user.getId());
        if (manager != null) result.setManagerDto(modelMapper.map(manager, ManagerDto.class));
        return result;
    }

    @Override
    @Transactional
    public UserDto updateClassById(Long id, Long classId) {
        if(!userRepository.findById(id).isPresent()) return null;
        userRepository.updateClassById(id, classId);
        User user = userRepository.findById(id).get();
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    @Transactional
    public LoginDto insertManager(UserDto userDto, int status) {
        if(userRepository.findByUid(userDto.getUserId()).isPresent()){
            throw new InvalidRequestException(ErrorCode.JOINED_USER_ID);
        }

        User user = userRepository.save(modelMapper.map(userDto, User.class));
        Manager manager = managerRepository.save(new Manager(0L, status, user));
        if(status != 0 && userDto.getClassRoomId() != null) classManagerRepository.save(new ClassManager(0L, user.getClassRoom(), user));

        LoginDto result = modelMapper.map(user, LoginDto.class);
        if(user.getClassRoom() != null) result.setClassRoomDto(modelMapper.map(user.getClassRoom(), ClassRoomDto.class));
        result.setManagerDto(modelMapper.map(manager, ManagerDto.class));
        return result;
    }
}
