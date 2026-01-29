package org.evan.project.controller;

import lombok.RequiredArgsConstructor;
import org.evan.project.model.entity.Item;
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
@RequestMapping("api/v1/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{id}")
    public Item getById(@PathVariable Long id,
                        @RequestParam(defaultValue = "false") boolean includeStock) {
        var item = itemService.getById(id);

        if (includeStock) {
            var stock = itemService.getRemainingStock(id);
        }

        return item;
    }

    @PostMapping
    public Item create(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{id}")
    public Item updateById(@PathVariable Long id, @RequestBody Item item) {
        return itemService.updateItem(id, item);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.delete(id);
    }
}
