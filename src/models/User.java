package models;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "Users")
@NamedQueries({
    @NamedQuery(
         // 全てのユーザーを表示する
        name = "getAllUsers",
        query = "SELECT u FROM User AS u ORDER BY u.id DESC"
    ),
    @NamedQuery(
         // 全てのユーザーを数える
        name = "getUsersCount",
        query = "SELECT COUNT(u) FROM User AS u"
    ),
    @NamedQuery(
         // メールアドレスの重複チェック
        name = "checkRegisteredMail_address",
        query = "SELECT COUNT(u) FROM User AS u WHERE u.mail_address = :mail_address"
    ),
    @NamedQuery(
        // お問い合わせをした全てのユーザー情報を表示する
        name = "getContactedUsers",
        query = "SELECT u FROM User AS u, Contact AS c WHERE u.id = c.user.id"
    ),
    @NamedQuery(
         // ログイン時のメールアドレスとパスワードチェック
        name = "checkLoginMail_addressAndPassword",
        query = "SELECT u FROM User AS u WHERE u.delete_flag = 0 AND u.mail_address = :mail_address AND u.password = :pass"
    )
})
//エンティティ
@Entity
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mail_address", nullable = false, unique = true)
    private String mail_address;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Column(name = "admin_flag", nullable = false)
    private Integer admin_flag;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    private Integer delete_flag;

    // Petモデルと多対多で結びつく
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "favorites",
                       joinColumns = @JoinColumn(name = "user_id"),
                       inverseJoinColumns = @JoinColumn(name = "pet_id"))
    List<Pet> favotitePets;

    // Contactモデルと多対多で結びつく
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "replies",
                       joinColumns = @JoinColumn(name = "send_user_id"),
                       inverseJoinColumns = @JoinColumn(name = "contacts_id"))
    Set<Contact> replyContacsUsers;

    // ゲッターとセッター
    public Integer getId() {
        return id;
    }


    public Set<Contact> getReplyContacs() {
        return replyContacsUsers;
    }


    public void setReplyContacs(Set<Contact> replyContacs) {
        this.replyContacsUsers = replyContacs;
    }

    public List<Pet> getFavotitePets() {
        return favotitePets;
    }

    public void setFavotitePets(List<Pet> favotitePets) {
        this.favotitePets = favotitePets;
    }

    public String getMail_address() {
        return mail_address;
    }

    public void setMail_address(String mail_address) {
        this.mail_address = mail_address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin_flag() {
        return admin_flag;
    }

    public void setAdmin_flag(Integer admin_flag) {
        this.admin_flag = admin_flag;
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

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }
}
