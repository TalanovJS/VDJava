package vdcom.base;

import vdcom.mapper.Mapper;

import java.util.*;

public class Value implements Mapper<Value, Double> {
    private String name;
    private final Map<Value, Double> relations;

    public Value(String name) {
        this.name = name;
        this.relations = new LinkedHashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Value, Double> getRelations() {
        return relations;
    }

    public void map(Value key, Double value) {
        if (value != null && !this.name.equals(key.getName())) {
            relations.put(key, value);
        }
    }

    public Double getValueFromMap(Value value) {
        return relations.get(value);
    }

    @Override
    public String toString() {
        return name;
    }
}