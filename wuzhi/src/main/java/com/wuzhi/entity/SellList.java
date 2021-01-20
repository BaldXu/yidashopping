package com.wuzhi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author ${author}
 * @since 2020-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SellList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private Integer storeId;

    private Integer cloTypeId;

    private Date time;

    private Integer amoney;

    private Integer num;

    /**
     * 发货地址
     */
    private String shippingAddress;

    /**
     * 收货地址
     */
    private String deliveryAddress;

    /**
     * 在库，租赁，已出售
     *
     */
    private String state;


}
