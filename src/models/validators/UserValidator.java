package models.validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
        public static List<String> validate(User u, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
            List<String> errors = new ArrayList<String>();

            String code_error = validateCode(u.getCode(), codeDuplicateCheckFlag);
            if(!code_error.equals("")) {
                errors.add(code_error);
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


        // ユーザーID
        private static String validateCode(String code, Boolean codeDuplicateCheckFlag) {
            // 必須入力チェック
            if (code == null || code.equals("")) {
                return "ユーザーIDを入力してください。";
            }

            // すでに登録されている社員番号との重複チェック
            if(codeDuplicateCheckFlag) {
                EntityManager em = DBUtil.createEntityManager();
                long users_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                        .setParameter("code", code)
                        .getSingleResult();
                em.close();
                if(users_count > 0) {
                    return "入力されたユーザーIDの情報はすでに存在しています。";
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







