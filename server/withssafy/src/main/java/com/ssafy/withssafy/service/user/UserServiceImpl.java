package com.ssafy.withssafy.service.user;

import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    /**
     * 새로운 사용자를 등록한다.
     * @param user
     * @return user
     */
    @Override
    public User insertUser(User user){
        if(userRepository.findByUid(user.getU_id()).isEmpty()) userRepository.save(user);
        else return null;
        return user;
    }

    /**
     * 해당 이메일을 가진 사용자를 삭제한다.
     * @param id
     * @return 해당 이메일을 가진 사용자가 있다면 삭제 후 true, 없다면 false
     */
    @Transactional
    @Override
    public Boolean deleteByUid(Long id){
        if(userRepository.findById(id).isEmpty()) return false;
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
    public Optional<User> updatePasswordByUid(Long id, String password) {
        if(userRepository.findById(id).isEmpty()) return null;
        userRepository.updatePasswordById(id, password);
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findById(Long u_id) {
        return userRepository.findById(u_id);
    }

    /**
     * 해당 아이디를 가진 사용자를 조회한다.
     * @param u_id
     * @return 해당 사용자 User
     */
    @Override
    public Optional<User> findByUid(String u_id) {
        return userRepository.findByUid(u_id);
    }

    /**
     * 전체 사용자를 조회한다.
     * @return List<User>
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    /**
     * 비밀번호와 아이디를 통해 사용자를 조회한다.
     * @param u_id
     * @param password
     * @return User
     */
    @Override
    public Optional<User> login(String u_id, String password) {
        return userRepository.login(u_id, password);
    }
}
