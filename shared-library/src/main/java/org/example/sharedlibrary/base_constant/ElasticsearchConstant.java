package org.example.sharedlibrary.base_constant;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class ElasticsearchConstant {
    public static final String ES_URL = "http://localhost:9200";


    public static String returnNoSpecialCharactersString(String input) {
        if (input == null) {
            return null;
        }

        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD).toLowerCase();
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        return pattern.matcher(normalizedString).replaceAll("").replaceAll("đ", "d");
    }

}