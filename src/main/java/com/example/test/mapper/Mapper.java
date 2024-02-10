package com.example.test.mapper;

public interface Mapper<T,V> {
    T convertEntityToDTO(V entity);
}
