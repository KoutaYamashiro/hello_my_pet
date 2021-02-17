package models.validators;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Favorite;
import models.Pet;
import models.User;
import utils.DBUtil;

public class FavoriteValidator {
    public static List<String> validate(Favorite f, Boolean favoriteCheckFlag) {
        List<String> error = new ArrayList<String>();

        String favorite_error = validateFavorite(f.getUser(), f.getPet(), favoriteCheckFlag);
        if(!favorite_error.equals("")) {
            error.add(favorite_error);
        }

        return error;
}

    private static String validateFavorite(User user, Pet pet, Boolean favoriteCheckFlag) {
        // すでにいいねされているペットID、ユーザーIDの重複チェック
        if(favoriteCheckFlag) {
            EntityManager em = DBUtil.createEntityManager();
            long checkMyFavoriteCount = (long)em.createNamedQuery("checkMyFavoriteCount", Long.class)
                                                            .setParameter("user", user)
                                                            .setParameter("pet", pet)
                                                            .getSingleResult();

            em.close();
            if(checkMyFavoriteCount > 0) {
                return "すでにペットにいいねしています。";
            }
        }
        return "";

    }
}
