package com.zzq.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzq.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
