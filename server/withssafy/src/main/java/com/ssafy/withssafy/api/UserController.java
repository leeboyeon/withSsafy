package com.ssafy.withssafy.api;

import com.ssafy.withssafy.entity.User;
import com.ssafy.withssafy.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    UserService userService;

    /**
     * 전체 사용자를 조회한다.
     * @return List<User>
     */
    @GetMapping()
    public ResponseEntity<List<User>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * 특정 사용자를 조회한다.
     * @param u_id
     * @return
     */
    @GetMapping("/{u_id}")
    public ResponseEntity<User> findByUid(@PathVariable("u_id") String u_id){
        return new ResponseEntity<>(userService.findById(u_id), HttpStatus.OK);
    }


    /**
     * 새로운 사용자를 등록한다.
     * @param user
     * @return 등록된 사용자의 정보
     */
    @PostMapping
    @ApiOperation(value = "해당 회원 정보로 가입한다.")
    public ResponseEntity<User> save(@RequestParam(value="User") User user){
        return new ResponseEntity<>(userService.insertUser(user), HttpStatus.OK);
    }

    /**
     * userEmail을 통해 사용자를 삭제한다.
     * @param userEmail
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "해당 이메일을 가진 유저를 삭제합니다.", response = Boolean.class)
    public ResponseEntity<Boolean> delete(@RequestParam(value="UserEmail")String userEmail){
        return new ResponseEntity<>(userService.deleteByUid(userEmail), HttpStatus.OK);
    }

    /**
     * 해당 ID를 가진 유저 비밀번호를 수정한다.
     * @param u_id, password
     * @return 변경된 User 정보
     */
    @PatchMapping
    @ApiOperation(value = "해당 이메일을 가진 유저 비밀번호를 수정 후 변경된 User를 반환한다")
    public ResponseEntity<User> update(@RequestParam(value="id")String u_id, @RequestParam(value="password") String password){
        return new ResponseEntity<>(userService.updatePasswordByUid(u_id, password), HttpStatus.OK);
    }
}
