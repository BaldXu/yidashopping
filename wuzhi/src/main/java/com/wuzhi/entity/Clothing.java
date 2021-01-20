package com.wuzhi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.CrossOrigin;

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
public class Clothing implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服装id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 服装名称
     */
    private String name;

    /**
     * 服装状态（在库，租赁，出售
     */
    private String state;

    /**
     * 单件押金
     */
    private Integer deposit;

    /**
     * 单件单日租金
     */
    private Integer rent;

    /**
     * 服装类型
     */
    private Integer cloTypeId;

    private Integer sellListId;

    private Integer rentListId;

    private Integer cost;

    private Integer storeId;

    private String imgUrl;

}
