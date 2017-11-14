package controllers;

import boundaries.Controller;
import boundaries.ControllerInfo;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SceneEngine {

    private static Map<String, Image> bwImgs;

    static private Stage primaryStage, popOutStage, loginStage;

    public static void setStages(Stage primaryStage) {
        SceneEngine.primaryStage = primaryStage;
        popOutStage = new Stage();
        loginStage = new Stage();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Stage getPopOutStage() {
        return popOutStage;
    }

    public static Stage getLoginScene() {
        return loginStage;
    }

    public static void closePopOut(){
        popOutStage.close();
    }

    public static void closeLogin() {
        loginStage.close();
    }

    public static Map<String, Image> getHospitalImageMap() {
        if(bwImgs == null) {
            bwImgs = new HashMap<>();
            // Populate image map
            bwImgs.put("L2", new Image("maps/L2.png"));
            bwImgs.put("L1", new Image("maps/L1.png"));
            bwImgs.put("G", new Image("maps/G.png"));
            bwImgs.put("1", new Image("maps/1.png"));
            bwImgs.put("2", new Image("maps/2.png"));
            bwImgs.put("3", new Image("maps/3.png"));
        }

        return bwImgs;
    } // TODO: make floors an enum instead of a string


    public static void display(Class<? extends Controller> newController, Stage stage, ControllerInfo info) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(newController.getResource("../"+newController.newInstance().getFXMLFileName()));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            // Get newly created controller
            Controller c = (Controller) loader.getController();
            if(info != null) {
                // Pass it data
                c.setControllerInfo(info);
            }

            c.setStage(stage);
            c.setScene(scene);
            stage.setScene(scene);
            stage.show();

        }catch(IOException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void display(Class<? extends Controller> newController, ControllerInfo info) {
        display(newController, getPrimaryStage(), info);
    }

}
