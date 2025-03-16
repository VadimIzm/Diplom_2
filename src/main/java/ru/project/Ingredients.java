package ru.project;
import java.util.ArrayList;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import java.util.List;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class Ingredients extends Endpoints{

    public ArrayList<String> ingredients;
    public Ingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Step("Формирование заказа с ингредиентами")
    public static Ingredients orderWithIngredients() {
        ValidatableResponse response = given()
                .spec(baseUri())
                .when()
                .get(ingredients())
                .then()
                .statusCode(200);
        ArrayList<String> ingredients = new ArrayList<>();
        List<String> bun = response.extract().jsonPath().getList("data.findAll{it.type =='bun'}._id");
        List<String> sauce = response.extract().jsonPath().getList("data.findAll{it.type =='sauce'}._id");
        List<String> main = response.extract().jsonPath().getList("data.findAll{it.type =='main'}._id");
        ingredients.add(bun.get(nextInt(0,bun.size())));
        ingredients.add(sauce.get(nextInt(0,sauce.size())));
        ingredients.add(main.get(nextInt(0,main.size())));
        return new Ingredients(ingredients);
    }

    @Step("Формирование заказа без ингредиентов")
    public static Ingredients orderWithoutIngredients(){
        ArrayList<String> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }

    @Step("Формирование заказа с неверным хешем ингредиентов")
    public static Ingredients orderWithIncorrectIngredients(){
        ArrayList<String> ingredients = new ArrayList<>();
        String someIngredient = "Abc";
        ingredients.add(someIngredient);
        return new Ingredients(ingredients);
    }
}