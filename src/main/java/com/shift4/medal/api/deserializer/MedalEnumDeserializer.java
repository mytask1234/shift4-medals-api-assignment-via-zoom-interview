package com.shift4.medal.api.deserializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.shift4.medal.api.enums.MedalEnum;

public class MedalEnumDeserializer extends JsonDeserializer<MedalEnum> {

    @Override
    public MedalEnum deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String medalName = jsonParser.getText();
        return MedalEnum.getMedalEnum(medalName);
    }
}
