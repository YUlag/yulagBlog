package com.yulag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.dto.TagListDto;
import com.yulag.domain.entity.Tag;
import com.yulag.domain.vo.PageVo;
import com.yulag.domain.vo.TagVo;

import java.util.List;

public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    List<TagVo> listAllTag();
}

