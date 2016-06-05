/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import moe.tristan.OpenDataParisBibliotheques.Model.Elements.Element;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Tristan9 on 03/06/2016.
 */
@SuppressWarnings("rawtypes")
public class APIHandler {

    private static final Gson gson = new Gson();

    @NotNull
    public static APICallResult executeAPICall() {
        LinkedList<HashMap> rawElementsList = new LinkedList<>();

        int numElements = extractNumhits(executeQuery(readElements(0, 1)));
        rawElementsList.add(executeQuery(readElements(0, 200)));
        return handleRawElements(rawElementsList);
    }

    private static HashMap executeQuery(String QUERY) {
        try {
            BufferedReader apiReader = new BufferedReader(
                    new InputStreamReader(
                            new URL(QUERY).openStream()
                    )
            );
            return gson.fromJson(apiReader, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap();
        }
    }

    @Contract(pure = true)
    private static String readElements(int start, int numberOfElements) {
        return "http://opendata.paris.fr/api/records/1.0/search/"
                + "?dataset=tous-les-documents-des-bibliotheques-de-pret"
                + "&rows="+numberOfElements
                + "&facet=langue"
                + "&facet=genre"
                + "&facet=theme"
                + "&facet=titre_de_serie"
                + "&facet=collection"
                + "&facet=annee"
                + "&facet=auteur"
                + "&facet=editeur"
                + "&facet=genre_theme_type"
                + "&facet=type_de_document"
                + "&facet=categorie_statistique_1"
                + "&facet=categorie_statistique_2";
    }

    private static int extractNumhits(HashMap infoQueryResult) {
        return (int) Double.parseDouble(infoQueryResult.get("nhits").toString());
    }


    private static APICallResult handleRawElements(LinkedList<HashMap> rawElementsList) {
        LinkedList<Element> elements = new LinkedList<>();

        for (HashMap rawElements : rawElementsList) {
            ArrayList records = (ArrayList) rawElements.get("records");

            //noinspection unchecked
            records.parallelStream()
                    .filter(element -> element != null)
                    .forEach(
                            element -> {
                                Element.ElementBuilder elemBuilder = Element.builder();
                                elemBuilder.recordID(
                                        (String) ((LinkedTreeMap) element).get("recordid")
                                );
                                elemBuilder.recordTimestamp(
                                        (String) ((LinkedTreeMap) element).get("record_timestamp")
                                );
                                //noinspection unchecked
                                elemBuilder.fields(
                                        (LinkedTreeMap) ((LinkedTreeMap) element).get("fields")
                                );
                                elements.add(elemBuilder.build());
                            }
                    );
        }

        return APICallResult.builder()
                .elementList(elements)
                .build();
    }
}
