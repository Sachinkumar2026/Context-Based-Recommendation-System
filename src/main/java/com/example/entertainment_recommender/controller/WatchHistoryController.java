package com.example.entertainment_recommender.controller;

import com.example.entertainment_recommender.service.WatchHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/watch")
@AllArgsConstructor
public class WatchHistoryController {
    private final WatchHistoryService watchHistoryService;

    @PostMapping
    public String saveWatch(@RequestParam Long userId,
                            @RequestParam Long contentId){
        watchHistoryService.saveWatch(userId,contentId);
        return "saved";
    }
}
