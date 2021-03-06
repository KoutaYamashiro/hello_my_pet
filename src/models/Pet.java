package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "pets")
@NamedQueries({
    @NamedQuery(
         // 全てのペットを表示する
        name = "getAllPets",
        query = "SELECT p FROM Pet AS p ORDER BY p.id DESC"
    ),
    @NamedQuery(
            // // あるuserが投稿したペットを表示する
           name = "getMyPetUser",
           query = "SELECT p.user FROM Pet AS p"
       ),
    @NamedQuery(
        // 全てのペット数を数える
        name = "getPetsCount",
        query = "SELECT COUNT(p) FROM Pet AS p"
    )
})
//エンティティ
@Entity
public class Pet {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Userモデルと多対一で結びつく
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "pet_breed", length = 255, nullable = false)
    private String pet_breed;

    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @Lob
    @Column(name = "appeal_point", nullable = false)
    private String appeal_point;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "pet_image" , length= 250, nullable = false)
    private String pet_image;

    @Column(name = "pet_price" , length= 250, nullable = false)
    private String pet_price;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    // Userモデルと多対多で結びつく
    @ManyToMany(mappedBy = "favotitePets", fetch = FetchType.EAGER)
    Set<User> favoritedUsers;

    // ゲッターとセッター
    public Integer getId() {
        return id;
    }

    public Set<User> getFavoritedUsers() {
        return favoritedUsers;
    }


    public void setFavoritedUsers(Set<User> favoritedUsers) {
        this.favoritedUsers = favoritedUsers;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPet_breed() {
        return pet_breed;
    }

    public void setPet_breed(String pet_breed) {
        this.pet_breed = pet_breed;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAppeal_point() {
        return appeal_point;
    }

    public void setAppeal_point(String appeal_point) {
        this.appeal_point = appeal_point;
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

    public String getPet_image() {
        return pet_image;
    }

    public void setPet_image(String pet_image) {
        this.pet_image = pet_image;
    }

    public String getPet_price() {
        return pet_price;
    }

    public void setPet_price(String pet_price) {
        this.pet_price = pet_price;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    }

