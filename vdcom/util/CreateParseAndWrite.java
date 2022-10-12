package vdcom.util;

import vdcom.base.Content;
import vdcom.base.Value;

import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import static java.lang.Double.*;
import static java.nio.file.Files.*;
import static java.util.stream.Collectors.*;

public class CreateParseAndWrite {

    public static List<Content> parseFileLines(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        boolean isEmpty = exists(path);

        if (!isEmpty) {
            createAndFillFile(fileName, path);
        }

        return lines(path)
                .map(str -> str.split(" "))
                .map(value -> new Content(
                        valueOf(value[0]),
                        new Value(value[1].toLowerCase()),
                        value[3].equals("?") ? null : valueOf(value[3]),
                        new Value(value[4].toLowerCase()))
                )
                .collect(toList());
    }

    private static void createAndFillFile(String fileName, Path path) throws IOException {

        String str = "1024 byte = 1 kilobyte\n" +
                     "2 bar = 12 ring\n" +
                     "16.8 ring = 2 pyramid\n" +
                     "4 hare = 1 cat\n" +
                     "5 cat = 0.5 giraffe\n" +
                     "1 byte = 8 bit\n" +
                     "15 ring = 2.5 bar\n" +
                     "1 pyramid = ? bar\n" +
                     "1 giraffe = ? hare\n" +
                     "1 wolf = ? hare\n" +
                     "0.5 byte = ? cat\n" +
                     "2 kilobyte = ? bit\n";

        boolean isEmpty = exists(path);
        createFile(Path.of(fileName));

        if(!isEmpty) {
            write(path, str.getBytes());
        }
    }

    public static void toResultFile(List<Content> content) throws IOException {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.##", decimalFormatSymbols);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);

        try(FileWriter writer = new FileWriter("result.txt")) {
            for (Content fc : content) {
                String errorLine = "Conversion not possible.\n";
                String resultLine = String.format("%s %s = %s %s\n",
                        decimalFormat.format(fc.getLeftDouble()),
                        fc.getLeftValue(),
                        decimalFormat.format(fc.getRightDouble()),
                        fc.getRightValue());

                if (fc.getRightDouble() == 0.0) {
                    writer.write(errorLine);
                } else {
                    writer.write(resultLine);
                }
            }
        }
    }
}
