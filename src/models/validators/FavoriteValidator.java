//package models.validators;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.EntityManager;
//
//import models.Favorite;
//import models.Pet;
//import utils.DBUtil;
//
//public class FavoriteValidator {
//    public static List<String> validate(Favorite f, Boolean favoriteCheckFlag) {
//        List<String> error = new ArrayList<String>();
//
//        String favorite_error = validateFavorite(f.getPet(), favoriteCheckFlag);
//        if(!favorite_error.equals("")) {
//            error.add(favorite_error);
//        }
//
//        return error;
//}
//
//    private static String validateFavorite(Pet pet, Boolean favoriteCheckFlag) {
//        // すでにいいねされているペットIDとの重複チェック
//        if(favoriteCheckFlag) {
//            EntityManager em = DBUtil.createEntityManager();
//            long favorites_count = (long)em.createNamedQuery("checkMyFavorite", Long.class).setParameter("pet", pet)
//                                                            .getSingleResult();
//            em.close();
//            if(favorites_count > 0) {
//                return "すでにペットにいいねしています。";
//            }
//        }
//
//        return "";
//    }
//}
