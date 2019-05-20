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
@Path("kweeter")
public class KweetResource {

    @Inject
    private KweetService kweetService;

    // ------------------ GET ------------------
    @GET
    @Path("/{id}")
    @JWTTokenNeeded
    public List<Kweet> getKweetByUser(@PathParam("id") Long id){
        return kweetService.getLastTenKweets(id);
    }

    @GET
    @Path("/{id}/getAll")
    @JWTTokenNeeded
    public List<Kweet> getAllKweetByUser(@PathParam("id") Long id){
        return kweetService.getAllKweets(id);
    }

    @GET
    @Path("/getAll/{filter}")
    @JWTTokenNeeded
    public List<Kweet> getAllKweetByFilter(@PathParam("filter") String message){
        return kweetService.findByFilterKweet(message);
    }

    // ------------------ POST ------------------
    @POST
    @Path("/{id}")
    //@JWTTokenNeeded
    public Response createKweet(@PathParam("id") Long id, Kweet kweet){
        kweetService.createKweet(kweet, id);
        return Response.ok(kweet).build();
    }

    @POST
    @Path("/{id}/kweet/{kweetId}")
    @JWTTokenNeeded
    public Response createKweetReaction(@PathParam("id") Long id, @PathParam("kweetId") Long kweetId, Kweet kweet){
        kweetService.addReaction(id, kweetId, kweet);
        return Response.ok(kweet).build();
    }

    // ------------------ PUT ------------------

    // ------------------ DELETE ------------------
    @DELETE
    @Path("/{id}/kweet/{kweetid}")
    public Response removeKweet(@PathParam("id") Long id, @PathParam("kweetid") Long kweetId){
        kweetService.removeKweet(kweetId, id);
        return Response.ok().build();
    }

}
