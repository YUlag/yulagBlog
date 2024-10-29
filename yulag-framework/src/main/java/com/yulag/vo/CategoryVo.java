package com.yulag.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 35238
 * @date 2023/7/20 0020 15:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//返回给前端的特定字段
public class CategoryVo {

    private Long id;
    private String name;

}