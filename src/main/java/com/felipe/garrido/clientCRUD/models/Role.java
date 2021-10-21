package com.felipe.garrido.clientCRUD.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="lh_role")
public class Role {

    @Id
    private  String _id;

    private ERole name;

    public  Role(){ }

    public Role (ERole name){this.name = name;}

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

}
