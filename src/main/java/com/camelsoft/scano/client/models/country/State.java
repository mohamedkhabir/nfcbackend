package com.camelsoft.scano.client.models.country;

import com.camelsoft.scano.client.models.File.File_model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "state")
public class State implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "state_id" )
    private Long id;
    @Column(name = "state_name")
    private String name;
    @Column(name = "state_code")
    private String state_code;
    @Column(name = "state_latitude")
    private String latitude;
    @Column(name = "state_longitude")
    private String longitude;
    @Column(name = "state_type")
    private String type;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinTable(name = "city_state", joinColumns = @JoinColumn(name = "state_id"), inverseJoinColumns = @JoinColumn(name = "city_id"))
    private List<City> cities;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File_model image;
    public State() {
    }

    public State(String name, String state_code, String latitude, String longitude, String type){
        this.name = name;
        this.state_code = state_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public File_model getImage() {
        return image;
    }

    public void setImage(File_model image) {
        this.image = image;
    }

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

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
    public void AddCities(City citie) {
        this.cities.add(citie);
    }
}
