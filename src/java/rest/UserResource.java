package rest;

        import entities.Kweet;
        import entities.User;
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

    // ------------------ GET ------------------
    @GET
    @Path("/{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public User getUserById(@PathParam("id") Long id){
        return userService.findUserById(id);
    }

    @GET
    @Path("users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}/kweets")
    public List<Kweet> getKweets(User user){
        return userService.getAllKweets(user);
    }

    @GET
    @Path("followers")
    public List<User> getUserFollowers(User user){
        return userService.getAllFollowers(user);
    }

    @GET
    @Path("following")
    public List<User> getUserFollowing(User user){
        return userService.getAllFollowing(user);
    }

    // ------------------ POST ------------------
    @POST
    public Response createUser(User user) {
        userService.createUser(user);
        return Response.ok(user).build();
    }

    // ------------------ PUT ------------------
    @PUT
    @Path("/{id}/update")
    public Response updateUser(User user){
        userService.updateUser(user);
        return Response.ok(user).build();
    }


    @PUT
    @Path("/{id}/addfollowing")
    public Response addFollowing(@PathParam("id") Long id, User following) {
        //userService.addFollowing(user, following);
        return Response.ok().build();
    }

    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/removeFollowing")
    public Response removeFollowing(@PathParam("id") Long id, User following) {
        //userService.removeFollowing(user, following);
        return Response.ok().build();
    }
}
