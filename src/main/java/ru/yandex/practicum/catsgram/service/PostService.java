package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final UserService userService;
    private final Map<Integer,Post> posts = new HashMap<>();

    private static Integer globalId = 0;

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

        posts.put(++id, post);
        return post;
    }

    public Post findPostById(Integer postId) {
        Post post = posts.get(postId);
         if (post == null) {
             throw new PostNotFoundException(String.format("Пост с id = %d не найден", postId));
         }
        return post;
    }

    public List<Post> findAllByUserEmail(String email, Integer size, String sort) {
        return posts.values().stream().filter(post -> email.equals(post.getAuthor())).sorted((post1, post2) -> {
            int comp = post1.getCreationDate().compareTo(post2.getCreationDate()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp;
            }
            return comp;
        }).limit(size).collect(Collectors.toList());
    }
}