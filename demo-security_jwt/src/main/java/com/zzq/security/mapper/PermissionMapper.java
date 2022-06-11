package com.zzq.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzq.security.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
}
