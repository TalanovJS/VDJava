package vdcom.mapper;

@FunctionalInterface
public interface Converter<F, T> {
    void calculate (F fromType, T toType);
}
