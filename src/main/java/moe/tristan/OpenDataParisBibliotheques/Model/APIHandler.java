/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Model;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import moe.tristan.OpenDataParisBibliotheques.Model.Element.InfoElement;
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
    private static final String QUERY_URL =
            "http://opendata.paris.fr/api/records/1.0/search/"
                    + "?dataset=tous-les-documents-des-bibliotheques-de-pret"
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

    private static final Gson gson = new Gson();

    @NotNull
    public static APICallResult executeAPICall() {
        try {
            BufferedReader apiReader = new BufferedReader(
                    new InputStreamReader(
                            new URL(QUERY_URL).openStream()
                    )
            );
            HashMap rawElements = gson.fromJson(apiReader, HashMap.class);
            return handleRawElements(rawElements);
        } catch (IOException e) {
            e.printStackTrace();
            return APICallResult.FaultyAPICallResult();
        }
    }


    private static APICallResult handleRawElements(HashMap rawElements) {
        InfoElement infoElement =
                InfoElement.builder()
                        .nhits(rawElements.get("nhits").toString())
                        .parameters((LinkedTreeMap) rawElements.get("parameters"))
                        .build();

        LinkedList<Element> elements = new LinkedList<>();

        ArrayList records = (ArrayList) rawElements.get("records");
        //noinspection unchecked
        records.parallelStream()
                .forEach(
                        element -> {
                            Element.ElementBuilder elemBuilder = Element.builder();
                            elemBuilder.recordID(
                                    (String) ((LinkedTreeMap) element).get("recordid")
                            );
                            elemBuilder.recordTimestamp(
                                    (String) ((LinkedTreeMap) element).get("record_timestamp")
                            );
                            elemBuilder.fields(
                                    (LinkedTreeMap) ((LinkedTreeMap) element).get("fields")
                            );
                            elements.add(elemBuilder.build());
                        }
                );



        return APICallResult.builder()
                .infoElement(infoElement)
                .elementList(elements)
                .build();
    }
}
