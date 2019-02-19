package rest;

import entities.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Stateless
@Path("users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("{id}")
    public User getUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }

    @GET
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @POST
    @Path("register")
    public String saveUser(User user){
        userService.createUser(user);
        return "User registered";
    }
}
