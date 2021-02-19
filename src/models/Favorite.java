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
                query = "SELECT f.id FROM Favorite AS f WHERE f.pet  = :pet AND f.user = :user"),
        // 閲覧ペットのいいねをカウント
        @NamedQuery(
                name= "getPetFavoritesCount",
                query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.pet  = :pet"),
        // ログインユーザーのいいねしたペット一覧情報を取得
        @NamedQuery(
                name = "getMyFavoritePets",
                query = "SELECT p FROM Pet AS p, Favorite AS f WHERE f.user = :user AND p.id = f.pet.id ORDER BY f.id DESC"),
        // ログインユーザーのいいねしたペット数を取得
        @NamedQuery(
                name= "getMyFavoritesCount",
                query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.user = :user"),
        // ログインユーザーがすでにいいねしているペットかをチェックする
        @NamedQuery(
                name = "checkMyFavoriteCount",
                query = "SELECT COUNT(f) FROM Favorite AS f WHERE f.user = :user AND f.pet  = :pet")
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
