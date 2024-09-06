package com.camelsoft.scano.client.models.Auth;


import com.camelsoft.scano.client.Enum.step;
import com.camelsoft.scano.client.models.Auth.Tools.CardRequests;
import com.camelsoft.scano.client.models.File.File_model;
import com.camelsoft.scano.client.models.UserTools.adminsocialmedia;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import com.camelsoft.scano.tools.Enum.Auth.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Entity
@Table(name = "users_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "user_name"),
                @UniqueConstraint(columnNames = "email")
        })
public class users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    @Length(min = 5, message = "*Your user name must have at least 5 characters")
    @NotEmpty(message = "*Please provide a user name")
    private String username;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "cardemail")
    private String cardemail;

    @JsonIgnore
    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name = "";
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "phone_number")
    private String phonenumber;
    @Column(name = "landline")
    private String landline;
    @Column(name = "fax")
    private String fax;
    @Column(name = "addresstype")
    private String addresstype;

    @Column(name = "address")
    private String address;

    @Column(name = "country")
    private String country;
    @Column(name = "area")
    private String area;

    @Column(name = "street")
    private String street;

    @Column(name = "building")
    private String building;

    @Column(name = "floor")
    private String floor;

    @Column(name = "unitnumberaddress")
    private String unitnumberaddress;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "city")
    private String city;
    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "status")
    private Boolean status = false;
    @Column(name = "company_name")
    private String company_name;
    @Column(name = "position")
    private String position;
    @Column(name = "website")
    private String website;
    @Column(name = "url")
    private String url;

    @Column(name = "steps")
    private step steps = step.SIGN_UP;
    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<socialmedia> socialmedias = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "companies_id")
    private company companies;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "couverture_id")
    private File_model couverture;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<nfc_card> card = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CardRequests> cardRequests = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "file_id")
    private File_model image;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "tutorial_id")
    private Set<File_model> files = new HashSet<>();

    @Column(name = "timestmp")
    private Date timestmp;

    public users() {
        this.timestmp = new Date();
    }

    public users(String username, String email, String password, String name, String lastname, String firstname, String phonenumber, String landline, String fax, String addresstype, String address, String country, String area, String street, String building, String floor, String unitnumberaddress, String nickname, String city, String company_name, String position, String website, String cardemail) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.cardemail = cardemail;
        this.phonenumber = phonenumber;
        this.landline = landline;
        this.fax = fax;
        this.addresstype = addresstype;
        this.address = address;
        this.country = country;
        this.area = area;
        this.street = street;
        this.building = building;
        this.floor = floor;
        this.unitnumberaddress = unitnumberaddress;
        this.nickname = nickname;
        this.city = city;
        this.company_name = company_name;
        this.position = position;
        this.website = website;
        this.timestmp = new Date();
    }

    public users(String username, String email, String password, String lastname, String firstname, Gender gender, String phonenumber, String country, String city, String cardemail) {
        this.username = username;
        this.cardemail = cardemail;
        this.email = email;
        this.password = password;
        this.name = firstname + " " + lastname;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.phonenumber = phonenumber == null ? "" : phonenumber;
        this.country = country == null ? "" : country;
        this.city = city;
        this.timestmp = new Date();
    }


    public users(String username, String email, String password, String lastname, String firstname, String cardemail) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.cardemail = cardemail;
        this.name = firstname + " " + lastname;
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = Gender.OTHER;
        this.phonenumber = "";
        this.country = "";
        this.timestmp = new Date();
    }

    public users(String username, String email, String password, String lastname, String firstname, String address, String building, String unitnumberaddress, String city, String cardemail) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = firstname + " " + lastname;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
        this.building = building;
        this.cardemail = cardemail;
        this.unitnumberaddress = unitnumberaddress;
        this.city = city;
        this.timestmp = new Date();
    }

    public Set<socialmedia> getSocialmedias() {
        return socialmedias;
    }

    public void setSocialmedias(Set<socialmedia> socialmedias) {
        this.socialmedias = socialmedias;
    }

    public File_model getCouverture() {
        return couverture;
    }

    public void setCouverture(File_model couverture) {
        this.couverture = couverture;
    }


    public void addSocialmedias(socialmedia book) {
        socialmedias.add(book);
        book.setUser(this);
    }

    public void removeSocialmedias(long tagId) {
        socialmedia media = this.socialmedias.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if (media != null) {
            this.socialmedias.remove(media);
            media.setUser(null);
        }
    }

    public Set<CardRequests> getCardRequests() {
        return cardRequests;
    }

    public void setCardRequests(Set<CardRequests> cardRequests) {
        this.cardRequests = cardRequests;
    }

    public Set<nfc_card> getCard() {
        return card;
    }

    public void setCard(Set<nfc_card> card) {
        this.card = card;
    }

    public Set<File_model> getFiles() {
        return files;
    }

    public void setFiles(Set<File_model> files) {
        this.files = files;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardemail() {
        return cardemail;
    }

    public void setCardemail(String cardemail) {
        this.cardemail = cardemail;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Gender getGender() {
        return gender;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public company getCompanies() {
        return companies;
    }

    public void setCompanies(company companies) {
        this.companies = companies;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public File_model getImage() {
        return image;
    }

    public void setImage(File_model image) {
        this.image = image;
    }

    public Date getTimestmp() {
        return timestmp;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setTimestmp(Date timestmp) {
        this.timestmp = timestmp;
    }

    public String getArea() {
        return area;
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getUnitnumberaddress() {
        return unitnumberaddress;
    }

    public void setUnitnumberaddress(String unitnumberaddress) {
        this.unitnumberaddress = unitnumberaddress;
    }

    public String getNickname() {
        return nickname;
    }

    public step getSteps() {
        return steps;
    }


    public void setSteps(step steps) {
        this.steps = steps;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}