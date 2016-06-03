/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques;

import javafx.application.Application;
import javafx.stage.Stage;
import moe.tristan.OpenDataParisBibliotheques.Model.APICallResult;
import moe.tristan.OpenDataParisBibliotheques.Model.APIHandler;

public class MainApp extends Application {


    public static void main(String... args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        APICallResult result = APIHandler.getAllElements();
        result.getElementList().parallelStream().forEach(System.out::println);
    }
}