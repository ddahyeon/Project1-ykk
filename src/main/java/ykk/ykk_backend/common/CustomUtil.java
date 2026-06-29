package ykk.ykk_backend.common;

import java.util.Random;

public class CustomUtil {
    public static String GenerateAccountNumber() {
        Random r = new Random();

        String part1 = String.format("%03d", r.nextInt(1000));
        String part2 = String.format("%02d", r.nextInt(100));
        String part3 = String.format("%06d", r.nextInt(1_000_000));

        return part1 + "-" + part2 + "-" + part3;
    }
}
