//package com.yulag.controller;
//
//import com.yulag.domain.ResponseResult;
//import com.yulag.domain.entity.Role;
//import com.yulag.domain.entity.User;
//import com.yulag.domain.vo.UserInfoAndRoleIdsVo;
//import com.yulag.enums.AppHttpCodeEnum;
//import com.yulag.exception.SystemException;
//import com.yulag.service.RoleService;
//import com.yulag.service.UserService;
//import com.yulag.utils.SecurityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/system/user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleService roleService;
//    /**
//     * 获取用户列表
//     */
//    @GetMapping("/list")
//    public ResponseResult list(User user, Integer pageNum, Integer pageSize) {
//        return userService.selectUserPage(user,pageNum,pageSize);
//    }
//
//    /**
//     * 新增用户
//     */
//    @PostMapping
//    public ResponseResult add(@RequestBody User user)
//    {
//        if(!StringUtils.hasText(user.getUserName())){
//            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
//        }
//        if (!userService.checkUserNameUnique(user.getUserName())){
//            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
//        }
//        if (!userService.checkPhoneUnique(user)){
//            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
//        }
//        if (!userService.checkEmailUnique(user)){
//            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
//        }
//        return userService.addUser(user);
//    }
//
//    /**
//     * 根据用户编号获取详细信息
//     */
//    @GetMapping(value = { "/{userId}" })
//    public ResponseResult getUserInfoAndRoleIds(@PathVariable(value = "userId") Long userId)
//    {
//        List<Role> roles = roleService.selectRoleAll();
//        User user = userService.getById(userId);
//        //当前用户所具有的角色id列表
//        List<Long> roleIds = roleService.selectRoleIdByUserId(userId);
//
//        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
//        return ResponseResult.okResult(vo);
//    }
//
//    /**
//     * 修改用户
//     */
//    @PutMapping
//    public ResponseResult edit(@RequestBody User user) {
//        userService.updateUser(user);
//        return ResponseResult.okResult();
//    }
//
//    /**
//     * 删除用户
//     */
//    @DeleteMapping("/{userIds}")
//    public ResponseResult remove(@PathVariable List<Long> userIds) {
//        if(userIds.contains(SecurityUtils.getUserId())){
//            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
//        }
//        userService.removeByIds(userIds);
//        return ResponseResult.okResult();
//    }
//}
