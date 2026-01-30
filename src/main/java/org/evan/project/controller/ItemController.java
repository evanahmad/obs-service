package org.evan.project.controller;

import jakarta.validation.Valid;
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
import org.evan.project.model.entity.Item;
import org.evan.project.model.request.ItemRequest;
import org.evan.project.model.response.ApiResponse;
import org.evan.project.model.response.ItemWithStockResponse;
import org.evan.project.service.ItemService;

@Path("/api/v1/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GET
    @Path("/{id}")
    public ApiResponse<?> getById(
            @PathParam("id") Long id,
            @QueryParam("includeStock") @DefaultValue("false") boolean includeStock
    ) {
        var item = itemService.getById(id);

        if (includeStock) {
            int stock = itemService.getRemainingStock(id);
            return ApiResponse.of(new ItemWithStockResponse(item, stock));
        }

        return ApiResponse.of(item);
    }

    @POST
    public ApiResponse<Item> create(@Valid ItemRequest request) {
        var item = Item.builder()
                .name(request.name())
                .price(request.price())
                .build();
        return ApiResponse.of(itemService.createItem(item));
    }

    @POST
    @Path("/{id}/top-up")
    public ApiResponse<String> topUp(
            @PathParam("id") Long id,
            @QueryParam("qty") int qty
    ) {
        itemService.topUpStock(id, qty);
        return ApiResponse.of("Stock updated successfully");
    }

    @DELETE
    @Path("/{id}")
    public ApiResponse<Void> delete(@PathParam("id") Long id) {
        itemService.delete(id);
        return ApiResponse.of(null);
    }
}
