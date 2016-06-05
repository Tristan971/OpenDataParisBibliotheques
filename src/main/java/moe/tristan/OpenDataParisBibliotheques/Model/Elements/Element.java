/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Model.Elements;

import com.google.gson.internal.LinkedTreeMap;
import javafx.beans.property.SimpleStringProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by Tristan9 on 03/06/2016.
 */

@Builder
public class Element {
    @Getter private final String recordID;
    @Getter private final String recordTimestamp;
    @Getter private final LinkedTreeMap<String, Object> fields;

    @Override
    public String toString() {
        return (String) fields.get("titre");
    }

    public SimpleStringProperty getProperty(CommonField commonField) {
        return new SimpleStringProperty(
                ""+this.fields.get(
                        commonField.toString()
                )
        );
    }
}
