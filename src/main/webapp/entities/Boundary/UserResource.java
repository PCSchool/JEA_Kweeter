package Boundary;
import Domain.User;
import Services.UserService;

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
        return userService.getUser(id);
    }

    /*@GET
    public List<User> getUsers(){
        return userService.getAllUsers();
    }*/

    @POST
    public User saveUser(User user){
        return userService.saveUser(user);
    }
}
