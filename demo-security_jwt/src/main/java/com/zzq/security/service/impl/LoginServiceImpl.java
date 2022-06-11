package com.zzq.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zzq.security.entity.*;
import com.zzq.security.mapper.*;
import com.zzq.security.service.LoginService;
import com.zzq.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginServiceImpl implements LoginService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserRoleMapper userRoleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;

    @Override
    public String login(String username, String password) {
        String result;
        log.info(password + "--------encode-------" + passwordEncoder.encode(password));
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, username)
        );
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            // login successful return token
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", user.getUserId());
            map.put("username", user.getUsername());

            //current user -> roleIds
            List<Long> roleIds = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>()
                    .eq(UserRole::getUserId, user.getUserId())
                    .select(UserRole::getRoleId)).stream().map(UserRole::getRoleId).collect(Collectors.toList());
            //current user -> roleNames
            Set<String> roleNames = roleMapper.selectList(new LambdaQueryWrapper<Role>()
                    .in(Role::getRoleId, roleIds)
            ).stream().map(Role::getRoleName).collect(Collectors.toSet());

            //current user -> permissionIds
            Set<Long> permissionIds = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>()
                    .in(RolePermission::getRoleId, roleIds)
            ).stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());

            //current user -> permissionNames
            Set<String> permissionNames = permissionMapper.selectList(new LambdaQueryWrapper<Permission>()
                    .in(Permission::getPermissionId, permissionIds)
            ).stream().map(Permission::getPermissionName).collect(Collectors.toSet());

            roleNames.addAll(permissionNames);
            map.put("authorities", roleNames);
            result = tokenService.generateToken(map);
        } else {
            result = "username or password error";
        }

        return result;
    }
}
