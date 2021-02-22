package models;

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

@Table(name= "contacts")
@NamedQueries({
    @NamedQuery(
            // 全ての問い合わせに関する情報を取得
            name = "getAllContacts",
            query = "SELECT c FROM Contact AS c ORDER BY c.id DESC"
        ),
        @NamedQuery(
             // 全ての問い合わせをカウント
            name = "getContactsCount",
            query = "SELECT COUNT(c) FROM Contact AS c"
        ),
        // ログインユーザーの問い合わせ一覧情報を取得
        @NamedQuery(
                name= "getMyAllContacts",
                query = "SELECT c FROM Contact AS c WHERE c.user = :user ORDER BY c.id DESC"),
        // ログインユーザーの問い合わせをカウント
        @NamedQuery(
                name= "getMyContactsCount",
                query = "SELECT COUNT(c) FROM Contact AS c WHERE c.user = :user")
    })

@Entity
public class Contact {
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name= "user_id", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name= "pet_id", nullable = false)
        private Pet pet;

//        @ManyToOne
//        @JoinColumn(name = "replies_id")
//        private Reply reply;

        @Lob
        @Column(name= "content", nullable = false)
        private String content;

        @Column(name= "created_at", nullable = false)
        private Timestamp created_at;

        // 返信  多対多のモデリング付加
        @ManyToMany(mappedBy = "replyContacsUsers", fetch = FetchType.EAGER)
        Set<User> ContactRepliesUsers;

        public Integer getId() {
            return id;
        }

      public Set<User> getContactRepliesUsers() {
      return ContactRepliesUsers;
  }

  public void setContactRepliesUsers(Set<User> contactRepliesUsers) {
      ContactRepliesUsers = contactRepliesUsers;
  }

//        public Reply getReply() {
//            return reply;
//        }
//
//        public void setReply(Reply reply) {
//            this.reply = reply;
//        }

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
