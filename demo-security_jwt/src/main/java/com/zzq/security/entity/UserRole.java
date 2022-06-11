package com.zzq.security.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@TableName("user_role")
public class UserRole {

    @TableId(value = "user_role_id", type = IdType.AUTO)
    private Long userRoleId;

    @TableField("user_id")
    private Long userId;

    @TableField("role_id")
    private Long roleId;
}
