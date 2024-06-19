package com.ecommerce.main.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ProductCategoryTypes {
    Fashion, Lifestyle, Health, Electronics, Groceries, Sports;

    public static ProductCategoryTypes getCorrectType(String category) {
        for (ProductCategoryTypes type : values()) {
            if (type.name().equalsIgnoreCase(category)) {
                return type;
            }
        }
        return null;
    }

    public static List<String> getValidCategories() {
        /*
        - values() is a method provided by the enum type. It returns an array containing all the enum constants in the
        order they are declared.
        - Arrays.stream(values()) converts the array returned by values() into a stream.
        - Enum::name is a method reference that refers to the name() method of the enum. The name() method returns the
        name of the enum constant as a string.
        - map(Enum::name) converts each enum constant in the stream to its corresponding name string.
        - Collectors.toList() is a collector that accumulates the elements of the stream into a List.
         */
        return Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());
    }
}