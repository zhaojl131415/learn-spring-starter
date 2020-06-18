package com.zhao.es.crawler.controller;

import com.zhao.es.crawler.DTO.ScrollDTO;
import com.zhao.es.crawler.service.ShakeSpeareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhaojinliang
 * @version 1.0
 * @description TODO
 * @date 2020-06-17 23:01
 */
@RestController
@RequestMapping("/api/shakespeare")
public class ShakeSpeareController {
    @Autowired
    private ShakeSpeareService shakeSpeareService;



    @GetMapping("/search_scroll")
    public ScrollDTO searchScroll(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "scrollId", required = false) String scrollId,
                                  @RequestParam(value = "size", required = false) Integer size) {
        return shakeSpeareService.searchScroll(keyword, scrollId, size);
    }
}
