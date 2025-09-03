package com.shift4.medal.enums;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.shift4.medal.exception.UnsupportedMedalException;

import lombok.Getter;

public enum MedalType {

    BRONZE("bronze", 1),
    SILVER("silver", 2),
    GOLD("gold", 3);

    private final String jsonPropName;

    @Getter
    private final int points;

    MedalType(String jsonPropName, int points) {
        this.jsonPropName = jsonPropName;
        this.points = points;
    }

    @JsonValue
    public String toJson() {
        return jsonPropName;
    }

    private static final Map<String, MedalType> map;

    static {

        final Map<String, MedalType> tmpMap = new HashMap<>();

        for (MedalType medalType : MedalType.values()) {

            tmpMap.put(medalType.name().trim().toUpperCase(), medalType);
        }

        map = Map.copyOf(tmpMap); // Returns an unmodifiable Map containing the entries of the given Map.
    }

    @JsonCreator
    public static MedalType fromJson(String name) {

        MedalType medalType = map.get(name.trim().toUpperCase());

        if (medalType == null) {
            throw new UnsupportedMedalException("Medal type '%s' is not supported.".formatted(name));
        }

        return medalType;
    }
}
