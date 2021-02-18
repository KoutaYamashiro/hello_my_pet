package models;

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

@Table(name= "contacts")
@NamedQueries({
    @NamedQuery(
            // 全ての問い合わせを取得
            name = "getAllContacts",
            query = "SELECT c FROM Contact AS c ORDER BY c.id DESC"
        ),
        @NamedQuery(
             // 全ての問い合わせをカウント
            name = "getContactsCount",
            query = "SELECT COUNT(c) FROM Contact AS c"
        ),
        // ログインユーザー自身問い合わせ内容を取得
        @NamedQuery(
                name= "getMyContacts",
                query = "SELECT c FROM Contact AS c WHERE c.user = :login_user ORDER BY c.id DESC"),
        // ログインユーザー自身問い合わせをカウント
        @NamedQuery(
                name= "getMyContactsCount",
                query = "SELECT COUNT(c) FROM Contact AS c WHERE c.user = :login_user")
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

        @Lob
        @Column(name= "content", nullable = false)
        private String content;

        @Column(name= "created_at", nullable = false)
        private Timestamp created_at;

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
