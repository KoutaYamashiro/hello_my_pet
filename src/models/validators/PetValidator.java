package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Pet;

public class PetValidator {
    public static List<String> validate(Pet p) {
        List<String> errors = new ArrayList<String>();

        // 入力欄に空欄がないかチェック
        String breed_error = _validatePet_Breed(p.getPet_breed());
        if(!breed_error.equals("")) {
            errors.add(breed_error);
        }

        String image_error = _validatePet_Image(p.getPet_image());
        if(!image_error.equals("")) {
            errors.add(image_error);
        }

        String price_error = _validatePet_Price(p.getPet_price());
        if(!price_error.equals("")) {
            errors.add(price_error);
        }

        String appeal_error = _validateAppeal_Point(p.getAppeal_point());
        if(!appeal_error.equals("")) {
            errors.add(appeal_error);
        }



        return errors;
    }


    private static String _validatePet_Price(String pet_price) {
        if(pet_price == null || pet_price.equals("")) {
            return "金額を入力してください。";
        }
        return "";
    }

    private static String _validatePet_Image(String pet_image) {
        if(pet_image == null || pet_image.equals("")) {
            return "画像を選択してください。";
        }
        return "";
    }

    private static String _validatePet_Breed(String pet_breed) {
        if(pet_breed == null || pet_breed.equals("")) {
            return "ペットの種類を入力してください。";
        }
        return "";
    }

    private static String _validateAppeal_Point(String appeal_point) {
        if(appeal_point == null || appeal_point.equals("")) {
            return "アピールポイントを入力してください。";
        }
        return "";
    }
}
