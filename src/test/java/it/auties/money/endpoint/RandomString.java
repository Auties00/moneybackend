package it.auties.money.endpoint;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@UtilityClass
public class RandomString {
    private final String[] DICTIONARY = "abcdefghijklmnopqrstuvwxyz123456789!£$%&/()=?^".split("");
    public String randomString(int length){
        final var random = new Random();
        return IntStream.range(0, length).mapToObj(e -> DICTIONARY[random.nextInt(DICTIONARY.length)]).collect(Collectors.joining());
    }
}
