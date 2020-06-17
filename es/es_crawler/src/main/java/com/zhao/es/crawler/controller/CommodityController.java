package com.zhao.es.crawler.controller;

import com.zhao.es.crawler.DTO.ScrollDTO;
import com.zhao.es.crawler.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> search(@RequestParam("keyword") String keyword,
                                            @RequestParam("pageNo") int pageNo,
                                            @RequestParam("pageSize") int pageSize) {
        return commodityService.search(keyword, pageNo, pageSize, false);
    }

    @GetMapping("/search_highlight")
    public List<Map<String, Object>> searchHighlight(@RequestParam("keyword") String keyword,
                                                     @RequestParam("pageNo") int pageNo,
                                                     @RequestParam("pageSize") int pageSize) {
        return commodityService.search(keyword, pageNo, pageSize, true);
    }


    @GetMapping("/search_scroll")
    public ScrollDTO searchScroll(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "scrollId", required = false) String scrollId,
                                  @RequestParam("size") int size) {
        return commodityService.searchScroll(keyword, scrollId, size);
    }

}
