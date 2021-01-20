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
public class CloType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 衣服型号ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 衣服的类型
     */
    private String type;


}
