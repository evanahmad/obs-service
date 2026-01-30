package org.evan.project.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.evan.project.model.entity.Order;
import org.evan.project.model.response.ApiResponse;
import org.evan.project.service.OrderService;

import java.util.List;

@Path("/api/v1/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GET
    @Path("/{id}")
    public ApiResponse<Order> getById(@PathParam("id") Long id) {
        return ApiResponse.of(orderService.getById(id));
    }

    @GET
    public ApiResponse<List<Order>> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        return ApiResponse.of(orderService.getAll(page, size));
    }

    @POST
    public ApiResponse<Order> create(
            @QueryParam("itemId") Long itemId,
            @QueryParam("quantity") int quantity
    ) {
        return ApiResponse.of(orderService.createOrder(itemId, quantity));
    }

    @DELETE
    @Path("/{id}")
    public ApiResponse<Void> delete(@PathParam("id") Long id) {
        orderService.delete(id);
        return ApiResponse.of(null);
    }
}
