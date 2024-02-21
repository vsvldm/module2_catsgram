package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.exception.InvalidInputParamsException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {

    private final PostService postService;

    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    public List<Post> lastPostsFriends(@RequestBody String params) {
        ObjectMapper mapper = new ObjectMapper();
        FriendsParams friendsParams;
        try {
            String paramsFromString = mapper.readValue(params, String.class);
            friendsParams = mapper.readValue(paramsFromString, FriendsParams.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Неверный формат json", e);
        }

        if (friendsParams != null) {
            List<Post> posts = new ArrayList<>();
            for (String friend : friendsParams.getFriends()) {
                posts.addAll(postService.findAllByUserEmail(friend, friendsParams.getSize(), friendsParams.getSort()));
            }
            return posts;
        } else {
            throw new InvalidInputParamsException("Неверно заполнены параметры");
        }
    }
}
class FriendsParams {
    private String sort;
    private Integer size;
    private List<String> friends;

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }
}
