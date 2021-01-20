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
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商户的账号id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    private Integer stockId;

    private String address;


}
