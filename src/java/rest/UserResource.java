package rest;

import controller.UserController;
import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jdk.nashorn.internal.objects.NativeJSON;
import net.minidev.json.JSONObject;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import services.UserService;

import javax.crypto.KeyGenerator;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import java.lang.reflect.Array;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static org.springframework.hateoas.jaxrs.JaxRsLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static rest.LoginResource.ONE_MINUTE_IN_MILLIS;

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
    //@JWTTokenNeeded
    public User getUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }

    @GET
    @Path("/profile/{username}")
    //@JWTTokenNeeded
    public List<User> getUserByUsername(@PathParam("username") String username){
        return userService.findUserByUsername(username);
    }

    @GET
    @Produces({"application/json"})
    //@JWTTokenNeeded
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}/followers")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //@JWTTokenNeeded
    public List<User> getUserFollowers(@PathParam("id") Long id){
        System.out.println("getUserFollower");
        return userService.getAllFollowers(id);
    }

    @GET
    @Path("/{id}/followings")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //@JWTTokenNeeded
    public List<User> getUserFollowing(@PathParam("id") Long id){
        System.out.println("getUserFollowing");
        return userService.getAllFollowing(id);
    }

    @POST
    @Path("/{id}/followings/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
   // @JWTTokenNeeded
    public Response addFollowing(@PathParam("id") Long id, @PathParam("followingId") Long followingId) {
        userService.addFollowing(id, followingId);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/followers/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //@JWTTokenNeeded
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
        User createdUser = null;
        try{
            createdUser = userService.createUser(user);
            createdUser.add(new Link("http://localhost:8080/maven/api/users/profile/" + createdUser.getIdUser()).withSelfRel());

            Link followersLink = ControllerLinkBuilder.linkTo(UserController.class).slash(createdUser.getIdUser()).slash("followers").withRel("followers");
            createdUser.add(followersLink);
            Link link = ControllerLinkBuilder.linkTo(UserController.class).slash(createdUser.getIdUser()).slash("followings").withRel("followings");
            createdUser.add(link);
            builder = Response.status(Response.Status.ACCEPTED);
            return builder.entity(createdUser).build();
        }catch (IllegalArgumentException ex){
            builder = Response.status(Response.Status.NOT_ACCEPTABLE);
            return builder.entity(ex).build();
        }
    }

    // ------------------ PUT ------------------
    @PUT
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //@JWTTokenNeeded
    public Response updateUser(@PathParam("id") Long id, User user){
        ResponseBuilder builder = Response.status(Response.Status.BAD_REQUEST);
        User updateUser = null;
        try{
            updateUser = userService.updateUser(user, id);
            builder = Response.status(Response.Status.ACCEPTED);
        }catch (IllegalArgumentException ex){
            builder = Response.status(Response.Status.ACCEPTED);
            return builder.entity(ex).build();
        }finally {
            return builder.entity(updateUser).build();
        }
    }

    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/following/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    //@JWTTokenNeeded
    public Response removeFollowing(@PathParam("id") Long id, @PathParam("followingId") Long following) {
        if(userService.removeFollowing(id, following)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/follower/{followerId}")
    //@JWTTokenNeeded
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
    //@JWTTokenNeeded
    public Response removeUser(@PathParam("id") Long id, @PathParam("removeUserId") Long removeUserId){
        if(userService.removeUser(id, removeUserId)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

}
