package com.andreaspost.gc.cachedb.service.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractEntityConverter<T, E> {

	public abstract T decode(E entity);

	/**
	 * 
	 * @param entityList
	 * @return
	 */
	public List<T> decode(List<E> entityList) {
		List<T> resultList = new ArrayList<>();

		if (entityList == null) {
			return resultList;
		}

		entityList.stream().forEach(elem -> resultList.add(decode(elem)));

		return resultList;
	}

	public Set<T> decode(Set<E> entityList) {
		Set<T> resultList = new TreeSet<>();

		if (entityList == null) {
			return resultList;
		}

		entityList.stream().forEach(elem -> resultList.add(decode(elem)));

		return resultList;
	}

	/**
	 * 
	 * @param elem
	 * @return
	 */
	public abstract E encode(T elem);

	/**
	 * 
	 * @param elemList
	 * @return
	 */
	public List<E> encode(List<T> elemList) {
		List<E> entityList = new ArrayList<>();

		if (elemList == null) {
			return entityList;
		}

		elemList.stream().forEach(elem -> entityList.add(encode(elem)));

		return entityList;
	}

	public Set<E> encode(Set<T> elemList) {
		Set<E> entityList = new TreeSet<>();

		if (elemList == null) {
			return entityList;
		}

		elemList.stream().forEach(elem -> entityList.add(encode(elem)));

		return entityList;
	}
}
