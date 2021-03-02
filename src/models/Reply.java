package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "replies")
@NamedQueries({
 // 全ての返信を表示する
    @NamedQuery(
            name = "getAllReplies",
            query = "SELECT r FROM Reply AS r ORDER BY r.id DESC"
        ),
    // 全ての返信を数える
    @NamedQuery(
            name = "getRepliesCount",
            query = "SELECT COUNT(r) FROM Reply AS r"
        ),
    // ログインユーザーへの返信一覧表示する
    @NamedQuery(
            name= "getMyAllReplies",
            query = "SELECT r FROM Reply AS r WHERE r.user = :user ORDER BY r.id DESC"),
    // ログインユーザーへの返信を数える
    @NamedQuery(
            name= "getMyRepliesCount",
            query = "SELECT COUNT(r) FROM Reply AS r WHERE r.user = :user")
})
//エンティティ
@Entity
public class Reply {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Userモデルと多対一で結びつく
    @ManyToOne
    @JoinColumn(name = "send_user_id", nullable = false)
    private User user;

    // Contactモデルと多対一で結びつく
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contacts_id", nullable = false)
    private Contact contact;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    // ゲッターとセッター
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
