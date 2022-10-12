package com.liuxn.yuzhong.project.os.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.FeedbackMapper;
import com.liuxn.yuzhong.project.os.domain.Feedback;
import com.liuxn.yuzhong.project.os.service.IFeedbackService;

/**
 * 意见反馈Service业务层处理
 *
 * @author liuxn
 * @date 2021-07-15
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

}
