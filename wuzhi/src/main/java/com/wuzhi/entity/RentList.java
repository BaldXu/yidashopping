package com.wuzhi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
public class RentList implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租赁表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customId;

    private Integer storeId;

    private Integer cloTypeId;

    private Integer num;

    private Integer deposit;

    private Integer rent;

    private Date retime;

    private String state;

    private Date time;

    private String deliveryAddress;

    private String shippingAddress;


}
