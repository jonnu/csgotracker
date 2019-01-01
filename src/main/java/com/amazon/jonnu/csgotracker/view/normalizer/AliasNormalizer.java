package com.amazon.jonnu.csgotracker.view.normalizer;

import java.util.Locale;

public class AliasNormalizer implements Normalizer {

    // Astralis.
    // device -> device
    // Magisk -> magisk
    // dupreeh -> dupreeh
    // gla1ve -> glaive
    // xyp9x -> zipex
    //
    // NAVI
    // Edward -> edward
    // Zeus -> zeus
    // flamie -> flamie
    // s1mple -> simple
    // electronic -> electronic
    //
    // Liquid
    // nitr0 -> nitro
    // NAF ->
    // EliGe ->   Eleege ??
    // Stewie2K
    // Twistzz
    @Override
    public String normalize(final String input) {
        return input.toLowerCase(Locale.US)
                .replace("0", "o")
                .replace("1", "i");
    }
}
