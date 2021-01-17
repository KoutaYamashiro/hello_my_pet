package models.validators;

import java.util.ArrayList;
import java.util.List;

import models.Pet;

public class PetValidator {
    public static List<String> validate(Pet p) {
        List<String> errors = new ArrayList<String>();

        String pet_name_error = _validatePet_Name(p.getPet_name());
        if(!pet_name_error.equals("")) {
            errors.add(pet_name_error);
        }

        String pet_type_error = _validatePet_Type(p.getPet_type());
        if(!pet_type_error.equals("")) {
            errors.add(pet_type_error);
        }

        String pet_breed_error = _validatePet_Breed(p.getPet_breed());
        if(!pet_breed_error.equals("")) {
            errors.add(pet_breed_error);
        }

        return errors;
    }

    private static String _validatePet_Name(String pet_name) {
        if(pet_name == null || pet_name.equals("")) {
            return "ペットの名前を入力してください。";
            }

        return "";
    }

    private static String _validatePet_Type(String pet_type) {
        if(pet_type == null || pet_type.equals("")) {
            return "ペットの種類を選択してください。";
            }

        return "";
    }

    private static String _validatePet_Breed(String pet_breed) {
        if(pet_breed == null || pet_breed.equals("")) {
            return "ペットの品種を入力してください。";
            }

        return "";
    }
}
