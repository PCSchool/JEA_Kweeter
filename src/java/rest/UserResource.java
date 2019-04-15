package rest;

import entities.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Stateless
@Path("users")
public class UserResource {

    @Inject
    private IJWTKey ijwtKey;

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

    @GET
    @Path("/profile/{username}")
    public List<User> getUserByUsername(@PathParam("username") String username){
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
        if(userService.addFollower(id, followingId)){
            return Response.ok().build();
        }
        return Response.noContent().build();

    }

    // ------------------ POST ------------------
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createUser(User user) {
        ResponseBuilder builder;
        if(userService.createUser(user)){
            builder = Response.status(Response.Status.CREATED);
        }else{
            builder = Response.status(Response.Status.NOT_ACCEPTABLE);
        }
        return builder.build();
    }

    @POST
    @Path("/login")
    @Consumes({"application/json"})

    public Response loginUser(User user) {
        try{
            User returnUser = userService.validateUser(user.getUsername(), user.getPassword());
            String token = this.ijwtKey.generateJWT(returnUser.getUsername());
            //generateToken(user.getUsername(), Arrays.asList("STANDARD", "MODERATOR", "ADMINISTRATOR"));
            return Response.ok().header(AUTHORIZATION, token).entity(returnUser).build();
        }catch(Exception e){
            return Response.status(UNAUTHORIZED).build();
        }
    }

    // ------------------ PUT ------------------
    @PUT
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response updateUser(@PathParam("id") Long id, User user){
        if(userService.updateUser(user, id)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/following/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response removeFollowing(@PathParam("id") Long id, @PathParam("followingId") Long following) {
        if(userService.removeFollowing(id, following)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/follower/{followerId}")
    public Response removeFollower(@PathParam("id") Long id, @PathParam("followerId") Long follower) {
        if(userService.removeFollower(id, follower)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/remove/{removeUserId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response removeUser(@PathParam("id") Long id, @PathParam("removeUserId") Long removeUserId){
        if(userService.removeUser(id, removeUserId)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

}
