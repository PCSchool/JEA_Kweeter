package rest;

import entities.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;

@Stateless
@Path("users")
public class UserResource {

    @Inject
    private UserService userService;

    @GET
    @Path("{id}")
    public User getUserById(@PathParam("id") Long id){
        return  null;
    }

    /*@GET
    public List<User> getUsers(){
        return userService.getAllUsers();
    }*/

    @POST
    public User saveUser(User user){

        return null;
        //return userService.saveUser(user);
    }
}
