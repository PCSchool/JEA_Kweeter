package controller;

import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.UserService;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/maven/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Long userId){
        //User user = userService.findUserById(userId);
        return userService.findUserById(userId);
    }

    @GetMapping(value = "/{userId}/followers")
    public Resources<User> getFollowersOfUser(@PathVariable final Long userId){
        Link link = ControllerLinkBuilder.linkTo(methodOn(UserController.class)
                .getFollowersOfUser(userId)).withSelfRel();
        return getResult(userService.getAllFollowing(userId), link, userId);
    }

    @GetMapping(value = "/{userId}/followings")
    public Resources<User> getFollowingsOfUser(@PathVariable final Long userId){
        Link link = ControllerLinkBuilder.linkTo(methodOn(UserController.class)
                .getFollowingsOfUser(userId)).withSelfRel();
        return getResult(userService.getAllFollowing(userId), link, userId);
    }

    private Resources<User> getResult(List<User> users, Link link, Long userId){
        for(final User user: users){
            Link selfLink = ControllerLinkBuilder.linkTo(methodOn(UserController.class)
                    .getUserById(user.getIdUser())).withSelfRel();
            System.out.println("result: " + selfLink);
            user.add(selfLink);
        }

        Resources<User> result = new Resources<User>(users, link);
        return result;
    }
}
