package rest;

import entities.Kweet;
import entities.User;
import services.KweetService;
import services.UserService;
import javax.ejb.Stateless;
import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Stateless
@Path("users")
public class UserResource {

    @Inject
    private UserService userService;

    // ------------------ GET ------------------
    @GET
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public User getUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }

    @POST
    @Path("/{username}")
    public List<User>  getUserByUsername(@PathParam("username") String username){
        return userService.findUserByUsername(username);
    }


    @GET
    @Produces({"application/json"})
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}/followers")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List<User> getUserFollowers(@PathParam("id") Long id){
        return userService.getAllFollowers(id);
    }

    @GET
    @Path("/{id}/followings")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List<User> getUserFollowing(@PathParam("id") Long id){
        return userService.getAllFollowing(id);
    }

    @POST
    @Path("/{id}/followings/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response addFollowing(@PathParam("id") Long id, @PathParam("followingId") Long followingId) {
        userService.addFollowing(id, followingId);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/followers/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response addFollower(@PathParam("id") Long id, @PathParam("followingId") Long followingId) {
        userService.addFollower(id, followingId);
        return Response.ok().build();
    }

    // ------------------ POST ------------------
    @POST
    @Path("register")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.ok(user).build();
    }

    @POST
    @Path("login")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response loginUser(User user) {
        //todo add loginUser with validation
        return Response.ok(user).build();
    }

    // ------------------ PUT ------------------
    //todo make update working
    @PUT
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response updateUser(@PathParam("id") Long id, User user){
        userService.updateUser(user, id);
        return Response.ok().build();
    }

    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/following/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response removeFollowing(@PathParam("id") Long id, @PathParam("followingId") Long following) {
        userService.removeFollowing(id, following);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/follower/{followerId}")
    public Response removeFollower(@PathParam("id") Long id, @PathParam("followerId") Long follower) {
        userService.removeFollower(id, follower);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}/remove/{removeUserId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response removeUser(@PathParam("id") Long id, @PathParam("removeUserId") Long removeUserId){
        userService.removeUser(id, removeUserId);
        return Response.ok().build();
    }

}
