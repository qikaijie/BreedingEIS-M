package com.liuxn.yuzhong.project.os.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liuxn.yuzhong.project.os.mapper.SpeciesMapper;
import com.liuxn.yuzhong.project.os.domain.Species;
import com.liuxn.yuzhong.project.os.service.ISpeciesService;

/**
 * 物种信息Service业务层处理
 *
 * @author liuxn
 * @date 2021-05-09
 */
@Service
public class SpeciesServiceImpl extends ServiceImpl<SpeciesMapper, Species> implements ISpeciesService {

}
