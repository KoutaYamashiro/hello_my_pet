package models.validators;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
        public static List<String> validate(User u, Boolean mail_addressDuplicateCheckFlag, Boolean passwordCheckFlag) {
            List<String> errors = new ArrayList<String>();

            String mail_address_error = validateMail_address(u.getMail_address(), mail_addressDuplicateCheckFlag);
            if(!mail_address_error.equals("")) {
                errors.add(mail_address_error);
            }

            String name_error = validateName(u.getName());
            if(!name_error.equals("")) {
                errors.add(name_error);
            }

            String password_error = validatePassword(u.getPassword(), passwordCheckFlag);
            if(!password_error.equals("")) {
                errors.add(password_error);
            }

            return errors;
        }


        // メールアドレス
        private static String validateMail_address(String mail_address, Boolean mail_addressDuplicateCheckFlag) {
            //  メールアドレスパターン
            String pattern = "^([a-zA-Z0-9])+([a-zA-Z0-9\\._-])*@([a-zA-Z0-9_-])+([a-zA-Z0-9\\._-]+)+$";
            Pattern p = Pattern.compile(pattern);

            // 必須入力チェック
            if (mail_address == null || mail_address.equals("")) {
                return "メールアドレスを入力してください。";
            }

            // メールアドレスチェック
            if (!p.matcher(mail_address).find()) {
                return "メールアドレスではありません。";
            }

            // すでに登録されているメールアドレスとの重複チェック
            if(mail_addressDuplicateCheckFlag) {
                EntityManager em = DBUtil.createEntityManager();
                long users_count = (long)em.createNamedQuery("checkRegisteredMail_address", Long.class)
                        .setParameter("mail_address", mail_address)
                        .getSingleResult();
                em.close();
                if(users_count > 0) {
                    return "入力されたメールアドレスはすでに存在しています。";
                }
            }

            return "";
        }

        // ユーザー名の必須入力チェック
        private static String validateName(String name) {
            if(name == null || name.equals("")) {
                return "氏名を入力してください。";
            }

            return "";
        }

        // パスワードの必須入力チェック
        private static String validatePassword(String password, Boolean passwordCheckFlag) {
            // パスワードを変更する場合のみ実行
            if(passwordCheckFlag && (password == null || password.equals(""))) {
                return "パスワードを入力してください。";
            }
            return "";
        }
}







