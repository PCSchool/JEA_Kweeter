package rest;

        import entities.Kweet;
        import entities.User;
        import org.eclipse.persistence.annotations.DeleteAll;
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
    @Path("getAll")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}/kweets")
    @Consumes({"application/json"})
    @Produces({"application/json"})
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

    @POST
    @Path("/{id}/addfollowing")
    public Response addFollowing(@PathParam("id") Long id, Long followingId) {
        userService.addFollowing(id, followingId);
        return Response.ok().build();
    }

    // ------------------ POST ------------------
    @POST
    @Path("register")
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




    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/removeFollowing")
    public Response removeFollowing(@PathParam("id") Long id, Long following) {
        userService.removeFollowing(id, following);
        return Response.ok().build();
    }

    @DELETE
    public Response removeUser(User user){
        userService.removeUser(user);
        return Response.ok().build();
    }

}
