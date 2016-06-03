/*
 * Copyright (c) 2016. Tristan Deloche
 * This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 */

package moe.tristan.OpenDataParisBibliotheques.Model;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tristan9 on 03/06/2016.
 */

@Builder
public class APICallResult {
    @Getter private final Element.InfoElement infoElement;
    @Getter private final List<Element> elementList;

    @Contract(" -> !null")
    public static APICallResult FaultyAPICallResult() {
        return new APICallResult(
                Element.InfoElement.FaultyInfoElement(),
                Collections.emptyList()
        );
    }
}
