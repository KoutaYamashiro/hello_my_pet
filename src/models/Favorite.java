package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "favorites")
@NamedQueries({
        // いいねを解除するペットIDを取得
        @NamedQuery(
                name= "getDestroyPet",
                query = "SELECT f.id FROM Favorite f WHERE f.pet  = :pet_id AND f.user = :login_user"),
        // いいねを解除するユーザーIDを取得
        @NamedQuery(
                name= "getDestroyUser",
                query = "SELECT f.id FROM Favorite f WHERE f.user = :login_user AND f.pet = :pet"),
        // ペットのすべてのいいねをカウント
        @NamedQuery(
                name= "getPetAllFavoritesCount",
                query = "SELECT COUNT(f) FROM  Favorite f, Pet p WHERE f.pet = f.pet"),
        // ログインユーザー自身のいいねしたペット一覧を取得
        @NamedQuery(
                name= "getMyFavorites",
                query = "SELECT f FROM Favorite f, User u WHERE f.pet = :login_user AND u = f.user ORDER BY f.id DESC"),
        // ログインユーザー自身のいいねしたペットカウントを取得
        @NamedQuery(
                name= "getMyFavoritesCount",
                query = "SELECT COUNT(f) FROM  Favorite f, User u WHERE f.user = :login_user AND u = f.user"),
        // ログインユーザーがすでにいいねしているペットのIDを取得
        @NamedQuery(
                name = "checkMyFavorite",
                query = "SELECT f.pet FROM Favorite f WHERE f.user = :login_user")
})
@Entity
public class Favorite {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    public Integer getId() {
        return id;
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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
