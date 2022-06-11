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
@TableName("role")
public class Role {

    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;

    @TableField("role_name")
    private String roleName;
}
