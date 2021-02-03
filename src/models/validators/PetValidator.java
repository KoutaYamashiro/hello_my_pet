package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Pet;

public class PetValidator {
    public static List<String> validate(Pet p) {
        List<String> errors = new ArrayList<String>();



        String pet_breed_error = _validatePet_Breed(p.getPet_breed());
        if(!pet_breed_error.equals("")) {
            errors.add(pet_breed_error);
        }

        String pet_image_error = _validatePet_Image(p.getPet_image());
        if(!pet_image_error.equals("")) {
            errors.add(pet_image_error);
        }


        String appeal_point_error = _validateAppeal_Point(p.getAppeal_point());
        if(!appeal_point_error.equals("")) {
            errors.add(appeal_point_error);
        }

        return errors;
    }

    private static String _validatePet_Breed(String pet_breed) {
        if(pet_breed == null || pet_breed.equals("")) {
            return "ペットの種類を入力してください。";
        }
        return "";
    }

    private static String _validatePet_Image(String pet_image) {
        if(pet_image == null || pet_image.equals("")) {
            return "ペットの写真を選択してください。";
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
