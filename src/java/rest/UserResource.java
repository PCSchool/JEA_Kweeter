package rest;

        import entities.Kweet;
        import entities.User;
        import org.eclipse.persistence.annotations.DeleteAll;
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
@ApplicationScoped
public class UserResource {

    @Inject
    private UserService userService;

    @Inject
    private KweetService kweetService;

    // ------------------ GET ------------------
    @GET
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public User getUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }

    @GET
    @Path("getAll")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}/getallkweets")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List<Kweet> getAllKweets(@PathParam("id") Long id){
        return userService.getAllKweets(id);
    }

    @GET
    @Path("/{id}/followers")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List<User> getUserFollowers(@PathParam("id") Long id){
        return userService.getAllFollowers(id);
    }

    @GET
    @Path("/{id}/following")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List<User> getUserFollowing(@PathParam("id") Long id){
        return userService.getAllFollowing(id);
    }

    @POST
    @Path("/{id}/addfollowing/{followingId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response addFollowing(@PathParam("id") Long id, @PathParam("followingId") Long followingId) {
        System.out.println(followingId);
        userService.addFollowing(id, followingId);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/getKweets")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public List<Kweet> getKweets(@PathParam("id") Long id){
        return kweetService.getKweets(id, 10);
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
    @Path("/{id}/addkweet")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response createKweet(@PathParam("id") Long id, Kweet kweet){
        kweetService.createKweet(kweet, id);
        return Response.ok(kweet).build();
    }

    // ------------------ PUT ------------------
    @PUT
    @Path("/{id}/update")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response updateUser(User user){
        userService.updateUser(user);
        return Response.ok().build();
    }

    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/removeFollowing")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response removeFollowing(@PathParam("id") Long id, Long following) {
        userService.removeFollowing(id, following);
        return Response.ok().build();
    }

    @DELETE
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Response removeUser(User user){
        userService.removeUser(user);
        return Response.ok().build();
    }

}
