package com.zhao.es.crawler.controller;

import com.zhao.es.crawler.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    @PostMapping("/crawl")
    public void crawl(@RequestParam("keyword") String keyword) {
        commodityService.crawl(keyword);
    }

    @GetMapping("/search")
    public void search(@RequestParam("keyword") String keyword,
                       @RequestParam("pageNo") int pageNo,
                       @RequestParam("pageSize") int pageSize) {
        commodityService.search(keyword, pageNo, pageSize, false);
    }

    @GetMapping("/search_highlight")
    public void searchHighlight(@RequestParam("keyword") String keyword,
                       @RequestParam("pageNo") int pageNo,
                       @RequestParam("pageSize") int pageSize) {
        commodityService.search(keyword, pageNo, pageSize, true);
    }

}
