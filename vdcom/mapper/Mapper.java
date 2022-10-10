package vdcom.mapper;

public interface Mapper<K, V> {

    void map(K key, V value);

}
