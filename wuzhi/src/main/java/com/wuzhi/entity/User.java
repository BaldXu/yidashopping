package com.wuzhi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("用户实体类 User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户表Id
     */
    @ApiModelProperty("用户ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称(商户账号将代表商店名
     */
    private String nickname;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 开通时间（商户将代表开店时间
     */
    private Date time;

    /**
     * 邮箱
     */
    private String email;


}
