package com.SpringSecurity.security.sources.converterToEntity;

/**Знаю, что есть уже готовый Converter но все же чтобы не забыть про функциональные интерфейсы и лямбды пишу его
 * Таже можно было бы использовать wildcard для определения границ*/
@FunctionalInterface
public interface  ConverterFromTo< T , C> {
    public  C Convert(T t);
}
