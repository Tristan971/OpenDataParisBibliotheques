/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Model;

import com.google.gson.internal.LinkedTreeMap;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Tristan9 on 03/06/2016.
 */

@Builder
public class Element {
    @Getter private final String recordID;
    @Getter private final String recordTimestamp;
    @Getter private final LinkedTreeMap<String, String> fields;

    @Override
    public String toString() {
        return fields.get("titre");
    }



    /**
     * Class managing the metadata of the resultset.
     * Google's TreeMap and type handling is literally dark magic ?
     */
    @Builder
    public static class InfoElement {
        @SuppressWarnings({
                "rawtypes",
                "MismatchedQueryAndUpdateOfCollection"
        })
        @Getter private final Map parameters;
        @Getter private final String nhits;

        @Override
        public String toString() {
            return "Hits : "+nhits+"\nParameters :"+parameters;
        }

        @Contract(" -> !null")
        public static InfoElement FaultyInfoElement() {
            return new InfoElement(Collections.emptyMap(), "0");
        }
    }
}
