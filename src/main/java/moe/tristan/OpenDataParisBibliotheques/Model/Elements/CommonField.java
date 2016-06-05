/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Model.Elements;

import org.jetbrains.annotations.Contract;

/**
 * Created by Tristan9 on 04/06/2016.
 */
public enum CommonField {
    TITLE("titre"),
    AUTHOR("auteur"),
    ID("id"),
    COTE("cote"),
    YEAR("annee"),
    CAT_STAT_1("categorie_statistique_1")
    ;

    private final String field;

    CommonField(String field) {
        this.field = field;
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return field;
    }
}
