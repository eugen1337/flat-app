package back.infrastructure.in.rest.endpoint;

import com.google.gson.Gson;

import back.DTO.FlatDTO;
import back.app.api.IApp;
import back.domain.calculator.Room;
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

    @POST
    @Consumes("application/json")
    @Produces("text/plain")
    public Response setRoomPlan(String strJSON) {
        Gson gson = new Gson();
        try {
            System.out.println(strJSON);
            FlatDTO flat = gson.fromJson(strJSON, FlatDTO.class);
            String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
            if (!app.validateToken(token)) {
                System.out.println("token validate problem");
                return Response.status(Response.Status.UNAUTHORIZED).entity("Ошибка авторизации").build();
            }

            String login = app.getUserInfo(token).get("login");
            System.out.println(login);
            String res = app.setFlatPlan(flat, login);
            System.out.println(res);
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
            room.setWallsLength(new double[] {
                    Double.parseDouble(length) / 100,
                    Double.parseDouble(length) / 100,
                    Double.parseDouble(width) / 100,
                    Double.parseDouble(width) / 100 });
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

    @GET
    public Response getFlatList() {
        try {
            String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

            if (!app.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Ошибка авторизации").build();
            }

            String login = app.getUserInfo(token).get("login");
            String res = app.getFlatList(login);
            if (res != null) {
                return Response.ok(res).build();
            } else {
                throw new Exception("invalid");
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @Path("/flats")
    @GET
    public Response getFlat(@QueryParam("id") String id) {
        try {
            String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");

            if (!app.validateToken(token)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Ошибка авторизации").build();
            }

            String login = app.getUserInfo(token).get("login");
            String res = app.getFlat(login, Integer.parseInt(id));
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
