package org.evan.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Item;
import org.evan.project.model.response.ApiResponse;
import org.evan.project.model.response.ItemWithStockResponse;
import org.evan.project.model.request.ItemRequest;
import org.evan.project.service.ItemService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public ApiResponse<?> getById(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") boolean includeStock
    ) {
        var item = itemService.getById(id);

        if (includeStock) {
            int stock = itemService.getRemainingStock(id);
            return ApiResponse.of(
                    new ItemWithStockResponse(item, stock)
            );
        }

        return ApiResponse.of(item);
    }

    @PostMapping
    public ApiResponse<Item> create(
            @Valid @RequestBody ItemRequest request
    ) {
        var item = Item.builder()
                .name(request.name())
                .price(request.price())
                .build();

        return ApiResponse.of(itemService.createItem(item));
    }

    @PutMapping("/{id}")
    public ApiResponse<Item> update(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequest request
    ) {
        var item = Item.builder()
                .name(request.name())
                .price(request.price())
                .build();

        return ApiResponse.of(itemService.updateItem(id, item));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ) {
        itemService.delete(id);
        return ApiResponse.of(null);
    }
}
