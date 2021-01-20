package com.wuzhi.mapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wuzhi.entity.StoreInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2020-11-18
 */
@Repository
public interface StoreInfoMapper extends BaseMapper<StoreInfo> {
}
