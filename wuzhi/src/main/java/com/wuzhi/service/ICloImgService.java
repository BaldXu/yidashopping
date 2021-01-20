package com.wuzhi.service;

import com.wuzhi.entity.CloImg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wuzhi.util.ImgSave;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2020-11-24
 */
public interface ICloImgService extends IService<CloImg> {

}
