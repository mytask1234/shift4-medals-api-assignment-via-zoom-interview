package com.shift4.medal.api.enums;

import java.util.HashMap;
import java.util.Map;

import com.shift4.medal.api.exception.UnsupportedMedalException;

import lombok.Getter;

@Getter
public enum MedalEnum {

    BRONSE(1),
    SILVER(2),
    GOLD(3);

    private int score;

    MedalEnum(int score) {
        this.score = score;
    }

    private static final Map<String, MedalEnum> medalEnumNameToMedalEnumMap;

    static {

        final Map<String, MedalEnum> tmpMap = new HashMap<>();

        for (MedalEnum medalEnum : MedalEnum.values()) {

            tmpMap.put(medalEnum.name().trim().toUpperCase(), medalEnum);
        }

        medalEnumNameToMedalEnumMap = Map.copyOf(tmpMap); // Returns an unmodifiable Map containing the entries of the given Map.
    }

    public static MedalEnum getMedalEnum(String medalEnumName) {

        MedalEnum medalEnum = medalEnumNameToMedalEnumMap.get(medalEnumName.trim().toUpperCase());
        
        if (medalEnum == null) {
            throw new UnsupportedMedalException(medalEnumName);
        }
        
        return medalEnum;
    }
}
