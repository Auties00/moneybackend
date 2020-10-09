package it.auties.money.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Map;

public interface JsonSerializable {
    @SneakyThrows
    default String toJson(){
        var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(this);
    }

    @SneakyThrows
    default Map<String, String> toJsonMap(){
        return new ObjectMapper().convertValue(this, new TypeReference<>() {});
    }
}
