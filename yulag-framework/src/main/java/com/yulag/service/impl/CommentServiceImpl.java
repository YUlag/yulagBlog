package com.yulag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yulag.constants.SystemConstants;
import com.yulag.domain.ResponseResult;
import com.yulag.domain.entity.Comment;
import com.yulag.enums.AppHttpCodeEnum;
import com.yulag.exception.SystemException;
import com.yulag.mapper.CommentMapper;
import com.yulag.service.CommentService;
import com.yulag.service.UserService;
import com.yulag.utils.BeanCopyUtils;
import com.yulag.domain.vo.CommentVo;
import com.yulag.domain.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(String commentType,Long articleId, Integer pageNum, Integer pageSize) {
         LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

        //对articleId进行判断，作用是得到指定的文章。如果是文章评论，才会判断articleId，避免友链评论判断articleId时出现空指针
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

         queryWrapper.eq(Comment::getArticleId,articleId);
         queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);

         // 只查对应类型的评论
         queryWrapper.eq(Comment::getType,commentType);

        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

        // 评论按时间排序
        List<Comment> sortedComments = page.getRecords().stream()
                .sorted(Comparator.comparing(Comment::getCreateTime).reversed())
                .collect(Collectors.toList());

        List<CommentVo> commentVos = xxToCommentList(sortedComments);

        // 查出子评论 只到二级
        commentVos.forEach(commentVo -> {
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        });

        return ResponseResult.okResult(new PageVo(commentVos, page.getTotal()));
    }

    //TODO 需要token没有实现
    @Override
    public ResponseResult addComment(Comment comment) {
        //限制用户在发送评论时，评论内容不能为空。如果为空就抛出异常
        if(!StringUtils.hasText(comment.getContent())){
            //AppHttpCodeEnum是我们写的枚举类，CONTENT_NOT_NULL代表提示''
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        //解决了四个字段没有值的情况，就可以直接调用mybatisplus提供的save方法往数据库插入数据(用户发送的评论的各个字段)了
        save(comment);
        //封装响应返回
        return ResponseResult.okResult();
    }

    private List<CommentVo> xxToCommentList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            // 获取评论人的id
            Long id = commentVo.getCreateBy();
            // 根据id获取评论人名称
            String username = userService.getById(id).getNickName();
            commentVo.setUsername(username);

            //查询根评论的用户昵称。怎么判断是根评论的用户呢，判断getToCommentUserId为-1，就表示这条评论是根评论
            if (commentVo.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                //然后把nickname字段(发这条根评论的用户昵称)的数据赋值给commentVo类的toCommentUserName字段
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);
        return xxToCommentList(comments);
    }
}
