package com.wuzhi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CloImg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 这个图片属于的服装ID
     */
    private Integer cloTypeId;

    /**
     * 图片的路径
     */
    private String imgUrl;

    /**
     * 图片的类型（首页缩略图、介绍页图
     */
    private String imgType;

    private Integer storeId;


}
