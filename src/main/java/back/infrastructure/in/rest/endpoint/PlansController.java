package back.infrastructure.in.rest.endpoint;

import com.google.gson.Gson;

import back.app.api.IApp;
import back.domain.Room;
import back.infrastructure.utils.qualifiers.Built;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@Path("/plans")
public class PlansController {
    @Context
    private HttpHeaders headers;

    @Inject
    @Built
    private IApp app;

    @Path("/rooms")
    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response setRoomPlan(String strJSON) {
        Gson gson = new Gson();
        try {
            Room room = gson.fromJson(strJSON, Room.class);

            String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

            if (!app.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Ошибка авторизации").build();
            }

            String login = app.getUserInfo(token).get("login");

            String res = app.setRoomPlan(room, login);
            if (res != null) {
                return Response.ok(res).build();
            } else {
                throw new Exception("invalid");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/area")
    @GET
    public Response getArea(@QueryParam("length") String length, @QueryParam("width") String width) {
        try {
            String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

            if (!app.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Ошибка авторизации").build();
            }

            Room room = new Room();
            room.setWallsLength(new int[] { Integer.parseInt(length), Integer.parseInt(length), Integer.parseInt(width),
                    Integer.parseInt(width) });
            room.setType("rectangle");

            String login = app.getUserInfo(token).get("login");
            String res = app.getArea(room, login);
            if (res != null) {
                return Response.ok(res).build();
            } else {
                throw new Exception("invalid");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
}
