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
    public List<Kweet> getKweets(@PathParam("id") Long id){
        return userService.getAllKweets(id);
    }

    @GET
    @Path("/{id}/followers")
    public List<User> getUserFollowers(@PathParam("id") Long id){
        return userService.getAllFollowers(id);
    }

    @GET
    @Path("/{id}/following")
    public List<User> getUserFollowing(@PathParam("id") Long id){
        return userService.getAllFollowing(id);
    }

    // ------------------ POST ------------------
    @POST
    @Path("register")
    public Response saveUser(User user) {
        userService.createUser(user);
        return Response.ok(user).build();
    }

    // ------------------ PUT ------------------
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
