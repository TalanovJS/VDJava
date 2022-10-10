package vdcom.service;

import vdcom.base.Content;
import vdcom.base.Value;

import java.util.*;

import static java.util.stream.Collectors.*;

public class ContentService {
    private final Map<String, Value> contentMap;
    private final static Double EMPTY = 0.0;

    public ContentService(Map<String, Value> contentMap) {
        this.contentMap = contentMap;
    }

    public static Map<String, Value> getMapFromFields(List<Content> entryList) {
        Map<String, Value> currentMap = new HashMap<>();

        List<Content> filled = entryList.stream()
                .filter(f -> !f.getLeftValue().getName().equals(f.getRightValue().getName()))
                .filter(f -> f.getRightDouble() != null)
                .collect(toList());

        filledContentReverse(filled);

        filled.forEach(content -> {
            Value leftValue = content.getLeftValue();
            Value rightValue = content.getRightValue();

            String leftString = content.getLeftValue().getName().toLowerCase();
            String rightString = content.getRightValue().getName().toLowerCase();

            if (currentMap.containsKey(leftString)) {
                leftValue = currentMap.get(leftString);
            }
            if (currentMap.containsKey(rightString)) {
                rightValue = currentMap.get(rightString);
            }

            leftValue.map(rightValue, content.getRightDouble() / content.getLeftDouble());
            rightValue.map(leftValue, content.getLeftDouble() / content.getRightDouble());

            if (!currentMap.containsValue(leftValue)) {
                currentMap.put(leftString, leftValue);
            }
            if (!currentMap.containsValue(rightValue)) {
                currentMap.put(rightString, rightValue);
            }
        });
        return currentMap;
    }

    public void update(Content fc) {
        if (contentMap.containsKey(fc.getLeftValue().getName())){
            List<Value> relations = new ArrayList<>();

            Value fillValue = contentMap.get(fc.getLeftValue().getName());
            Value emptyValue = contentMap.get(fc.getRightValue().getName());

            Set<Value> fillList = fillValue.getRelations().keySet();

            fillList.forEach(fill -> {
                relations.add(fill);
                Double value1 = fillValue.getValueFromMap(fill);

                for (Value rel : relations) {
                    Double value2 = rel.getValueFromMap(emptyValue);

                    if (value2 != null) {
                        Double cnt = fc.getLeftDouble();
                        fc.setRightDouble(value2 * value1 * cnt);
                    } else {
                        fc.setRightDouble(EMPTY);
                    }
                }
            });
        } else {
            fc.setRightDouble(EMPTY);
        }
    }

    public static List<Content> emptyFields(List<Content> entryList) {
        return entryList.stream()
                .filter(content -> content.getRightDouble() == null)
                .collect(toList());
    }

    private static void filledContentReverse(List<Content> filled) {
        Value reverseValue;
        Double reverseDouble;

        List<Content> collect = filled.stream()
                .filter(c -> c.getRightDouble() != null)
                .collect(toList());

        for (Content f : collect) {
            if (f.getLeftDouble() > f.getRightDouble()) {
                reverseDouble = f.getLeftDouble();
                reverseValue = f.getLeftValue();
                f.setLeftDouble(f.getRightDouble());
                f.setLeftValue(f.getRightValue());
                f.setRightDouble(reverseDouble);
                f.setRightValue(reverseValue);
            }
        }
    }
}
