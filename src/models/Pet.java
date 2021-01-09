package models;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "pets")
@NamedQueries({
    @NamedQuery(
        name = "getAllPets",
        query = "SELECT r FROM Pet AS r ORDER BY r.id DESC"
    ),
    @NamedQuery(
        name = "getPetsCount",
        query = "SELECT COUNT(r) FROM Pet AS r"
    ),
    @NamedQuery(
        name = "getMyAllPets",
        query = "SELECT r FROM Pet AS r WHERE r.user = :user ORDER BY r.id DESC"
    ),
    @NamedQuery(
        name = "getMyPetsCount",
        query = "SELECT COUNT(r) FROM Pet AS r WHERE r.user = :user"
    )
})
@Entity
public class Pet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "pet_date", nullable = false)
    private Date pet_date;

    @Column(name = "pet_type", length = 255, nullable = false)
    private String pet_type;

    @Column(name = "pet_breed", length = 255, nullable = false)
    private String pet_breed;

    @Column(name = "pet_name", length = 255, nullable = false)
    private String pet_name;

    @Column(name = "age", length = 255, nullable = false)
    private String age;

    @Column(name = "home_town", length = 255, nullable = false)
    private String home_town;

    @Lob
    @Column(name = "nemo", nullable = false)
    private String nemo;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    @Column(name = "image_url" , length= 250, nullable = false)
    private String image_url;

    @Column(name = "favorite", length= 250, nullable = false)
    private String favorite;

    public Integer getId() {
        return id;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getPet_date() {
        return pet_date;
    }

    public void setPet_date(Date pet_date) {
        this.pet_date = pet_date;
    }

    public String getPet_type() {
        return pet_type;
    }

    public void setPet_type(String pet_type) {
        this.pet_type = pet_type;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public String getPet_name() {
        return pet_name;
    }

    public void setPet_name(String pet_name) {
        this.pet_name = pet_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHome_town() {
        return home_town;
    }

    public void setHome_town(String home_town) {
        this.home_town = home_town;
    }

    public String getNemo() {
        return nemo;
    }

    public void setNemo(String nemo) {
        this.nemo = nemo;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
