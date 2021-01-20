package com.wuzhi.service.impl;

import com.wuzhi.entity.CustomerInfo;
import com.wuzhi.mapper.CustomerInfoMapper;
import com.wuzhi.service.ICustomerInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-11-18
 */
@Service
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements ICustomerInfoService {

}
