package com.yulag.controller;

import com.yulag.annotation.MySystemlog;
import com.yulag.domain.ResponseResult;
import com.yulag.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkService linkService;


    @GetMapping("/getAllLink")
    @MySystemlog(xxbusinessName = "获取友链")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }

}