package rest;

import entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jdk.nashorn.internal.objects.NativeJSON;
import net.minidev.json.JSONObject;
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
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
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
    @JWTTokenNeeded
    public User getUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }

    @GET
    @Path("/profile/{username}")
    @JWTTokenNeeded
    public List<User> getUserByUsername(@PathParam("username") String username){
        return userService.findUserByUsername(username);
    }

    @GET
    @Produces({"application/json"})
    @JWTTokenNeeded
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}/followers")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @JWTTokenNeeded
    public List<User> getUserFollowers(@PathParam("id") Long id){
        return userService.getAllFollowers(id);
    }

    @GET
    @Path("/{id}/followings")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @JWTTokenNeeded
    public List<User> getUserFollowing(@PathParam("id") Long id){
        return userService.getAllFollowing(id);
    }

    @POST
    @Path("/{id}/followings/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @JWTTokenNeeded
    public Response addFollowing(@PathParam("id") Long id, @PathParam("followingId") Long followingId) {

        userService.addFollowing(id, followingId);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/followers/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @JWTTokenNeeded
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
            builder = Response.status(Response.Status.ACCEPTED);
            return builder.entity(createdUser).build();
        }catch (IllegalArgumentException ex){
            builder = Response.status(Response.Status.NOT_ACCEPTABLE);
            return builder.entity(ex).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response loginUser(User user) {
        try{
            User returnUser = userService.validateUser(user.getUsername(), user.getPassword());
            String token = issueToken(Long.toString(returnUser.getId()));
            JSONObject jsonToken = new JSONObject();
            jsonToken.put("access_token", "Bearer " + token);
            return Response.ok(jsonToken).header(AUTHORIZATION, token).build();
        }catch(Exception e){
            return Response.noContent().build();
        }
    }

    @POST
    @Path("/loginAuth")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response loginAuthUser(User user) {
        try{
            User returnUser = userService.validateUser(user.getUsername(), user.getPassword());
            return Response.ok().entity(returnUser).build();
        }catch(Exception e){
            return Response.noContent().build();
        }
    }

    private String issueToken(String login) {
        //Key key = keyGenerator.generateKey();
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        Date issuedAt = new Date();
        long time = issuedAt.getTime();
        Date expirationDate = new Date(time + (15 * ONE_MINUTE_IN_MILLIS));

        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return jwtToken;
    }

    // ------------------ PUT ------------------
    @PUT
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @JWTTokenNeeded
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
    @JWTTokenNeeded
    public Response removeFollowing(@PathParam("id") Long id, @PathParam("followingId") Long following) {
        if(userService.removeFollowing(id, following)){
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/follower/{followerId}")
    @JWTTokenNeeded
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
