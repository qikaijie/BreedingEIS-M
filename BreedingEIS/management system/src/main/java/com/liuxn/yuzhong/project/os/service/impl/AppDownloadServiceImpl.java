package com.liuxn.yuzhong.project.os.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.AppDownloadMapper;
import com.liuxn.yuzhong.project.os.domain.AppDownload;
import com.liuxn.yuzhong.project.os.service.IAppDownloadService;

/**
 * app版本下载Service业务层处理
 *
 * @author liuxn
 * @date 2021-07-17
 */
@Service
public class AppDownloadServiceImpl extends ServiceImpl<AppDownloadMapper, AppDownload> implements IAppDownloadService {

}
