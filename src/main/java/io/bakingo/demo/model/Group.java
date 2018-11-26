package io.bakingo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "item_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "*Nhập tên hợp lệ")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="group")
    private Set<Item> items;

    public Group() {}

    public Group(@NotEmpty(message = "*Please provide a valid name") String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Integer getItemsCount() {
        return getItems().size();
    }
}
