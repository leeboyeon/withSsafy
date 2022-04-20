package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    /**
     * 새로운 사용자를 등록한다.
     * @param user
     * @return user
     */
    public User insertUser(User user){
        userRepository.save(user);
        return user;
    }

    /**
     * 해당 이메일을 가진 사용자를 삭제한다.
     * @param u_id
     * @return 해당 이메일을 가진 사용자가 있다면 삭제 후 true, 없다면 false
     */
    public Boolean deleteByUid(String u_id){
        if(userRepository.findByUid(u_id) == null) return false;
        userRepository.deleteByUid(u_id);
        return true;
    }

    /**
     * 해당 사용자의 아이디를 가진 유저의 비밀번호를 수정한다.
     * @param u_id
     * @param password
     * @return 해당 사용자 User 정보
     */
    public User updatePasswordByUid(String u_id, String password) {
        if(userRepository.findByUid(u_id) == null) return null;
        userRepository.updatePasswordByEmail(u_id, password);
        return userRepository.findByUid(u_id);
    }

    /**
     * 해당 아이디를 가진 사용자를 조회한다.
     * @param u_id
     * @return 해당 사용자 User
     */
    public User findById(String u_id) {
        return userRepository.findByUid(u_id);
    }

    /**
     * 전체 사용자를 조회한다.
     * @return List<User>
     */
    public List<User> findAll() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
