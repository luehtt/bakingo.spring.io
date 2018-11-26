package io.bakingo.demo.service;

import io.bakingo.demo.config.WebSlugger;
import io.bakingo.demo.model.Item;
import io.bakingo.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service("itemService")
public class ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(@Qualifier("itemRepository") ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        Timestamp timestamp = new Timestamp((new Date().getTime()));
        item.setEnabled(true);
        item.setCreated(timestamp);
        item.setUpdated(timestamp);
        if (item.getImage() != null) {
            try {
                MultipartFile file = item.getImage();
                String name = file.getOriginalFilename();
                String slug;
                if (name == null) slug = new Timestamp((new Date().getTime())).toString();
                else {
                    int dot = name.lastIndexOf('.');
                    slug = WebSlugger.make(name.substring(0, dot)) + '.' + name.substring(dot + 1);
                }

                InputStream stream = file.getInputStream();
                Files.copy(stream, Paths.get("src/main/resources/media/item/" + slug), StandardCopyOption.REPLACE_EXISTING);
                item.setPhoto(slug);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return itemRepository.save(item);
    }

    public List<Item> findAll(boolean enabled) {
        return itemRepository.findAll()
                .stream().filter(x -> x.getEnabled() == enabled).sorted(Comparator.comparing(Item::getName))
                .collect(Collectors.toList());
    }

    public List<Item> findByGroupId(Integer id) {
        return itemRepository.findAll()
                .stream().filter(x -> (int) x.getGroup().getId() == id).sorted(Comparator.comparing(Item::getName))
                .collect(Collectors.toList());
    }

    public Item findById(Integer id) {
        return itemRepository.findById(id).get();
    }

    public Item update(Item item, Integer id) {
        Item get = findById(id);
        Timestamp timestamp = new Timestamp((new Date().getTime()));
        get.setName(item.getName());
        get.setInfo(item.getInfo());
        get.setGroup(item.getGroup());
        get.setEnabled(item.getEnabled());
        get.setPrice(item.getPrice());
        get.setDiscount(item.getDiscount());
        get.setUpdated(timestamp);

        if (item.getImage() != null) {
            try {
                MultipartFile file = item.getImage();
                String name = file.getOriginalFilename();
                String slug;
                if (name == null) slug = new Timestamp((new Date().getTime())).toString();
                else {
                    int dot = name.lastIndexOf('.');
                    slug = WebSlugger.make(name.substring(0, dot)) + '.' + name.substring(dot + 1);
                }

                InputStream stream = file.getInputStream();
                Files.copy(stream, Paths.get("src/main/resources/media/item/" + slug), StandardCopyOption.REPLACE_EXISTING);
                if (get.getPhoto() != null)
                    Files.delete(Paths.get("src/main/resources/media/item/" + get.getPhoto()));
                get.setPhoto(slug);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return itemRepository.save(get);
    }

    public Item delete(Integer id) {
        Item get = findById(id);
        itemRepository.delete(get);
        itemRepository.flush();
        return get;
    }
}
