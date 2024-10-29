package com.yulag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.constants.SystemConstants;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Link;
import com.yulag.mapper.LinkMapper;
import com.yulag.service.LinkService;
import com.yulag.utils.BeanCopyUtils;
import com.yulag.domain.vo.LinkVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);

        List<Link> list = list(lambdaQueryWrapper);

        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
