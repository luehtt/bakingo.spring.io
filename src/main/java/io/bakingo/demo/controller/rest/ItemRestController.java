package io.bakingo.demo.controller.rest;

import io.bakingo.demo.model.Item;
import io.bakingo.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemRestController {

    private ItemService itemService;

    @Autowired
    public ItemRestController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item")
    public List<Item> get(){
        return itemService.findAll(true);
    }

    @GetMapping("/item/{id}")
    public Item get(@PathVariable(value = "id") Integer id){
        return itemService.findById(id);
    }

    @GetMapping("/random/item/{id}")
    public List<Item> randomById(@PathVariable(value = "id") Integer id) {
        Item get = itemService.findById(id);
        if (get == null) return new ArrayList<>();
        List<Item> items = itemService.findByGroupId(get.getGroup().getId());
        items.removeIf(x -> (int) x.getId() == id);
        Collections.shuffle(items);
        int n = items.size();
        if (n > 6) return items.subList(0, 6);
        else return items;
    }

    @GetMapping("/random/item")
    public List<Item> random() {
        List<Item> items = itemService.findAll(true);
        Collections.shuffle(items);
        int n = items.size();
        if (n > 12) return items.subList(0, 12);
        else return items;
    }

}
