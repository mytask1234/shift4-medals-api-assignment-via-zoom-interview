package com.shift4.medal.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.shift4.medal.api.deserializer.MedalEnumDeserializer;
import com.shift4.medal.api.enums.MedalEnum;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RgisterMedalRequest {

    @JsonDeserialize(using = MedalEnumDeserializer.class)
    private MedalEnum medalType;
}
