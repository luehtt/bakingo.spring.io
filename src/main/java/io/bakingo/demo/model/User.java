package io.bakingo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "email")
    @Email(message = "*Nhập email hợp lệ")
    @NotEmpty(message = "*Nhập email hợp lệ")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    @Length(min = 8, message = "*Mật khẩu tối thiểu 8 kí tự")
    @NotEmpty(message = "*Nhập mật khẩu hợp lệ")
    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    @Column(name = "name")
    @NotEmpty(message = "*Nhập họ tên hợp lệ")
    private String name;

    @JsonIgnore
    @Column(name = "address")
    private String address;

    @JsonIgnore
    @Column(name = "phone")
    private String phone;

    @JsonIgnore
    @Column(name = "enabled")
    private Boolean enabled;

    @JsonIgnore
    @Column(name = "created_at")
    private Timestamp created;
    @JsonIgnore
    @Column(name = "updated_at")
    private Timestamp updated;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() { }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
