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
@TableName("permission")
public class Permission {

    @TableId(value = "permission_id", type = IdType.AUTO)
    private Long permissionId;

    @TableField("permission_name")
    private String permissionName;
}
