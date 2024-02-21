package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.*;

@Service
public class PostService {
    private final UserService userService;
    private final Map<Integer,Post> posts = new HashMap<>();

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, Integer from, String sort) {
        return posts.values().stream().sorted((post1, post2) -> {
           int comp =  post1.getCreationDate().compareTo(post2.getCreationDate());
           if(sort.equals("desc")) {
               comp = -1 * comp;
           }
           return comp;
        }). skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {
        if (userService.findUserByEmail(post.getAuthor()) == null) {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }

        posts.put(post.getId(), post);
        return post;
    }

    public Post findPostById(Integer postId) {
        return posts.get(postId);
    }
}