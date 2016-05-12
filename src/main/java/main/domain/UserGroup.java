package main.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by martijn on 12-5-2016.
 */
@Entity
@Table(name = "User_Group")
public class UserGroup implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public UserGroup() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

