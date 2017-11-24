package com.andreaspost.gc.cachedb.service.converter;

import com.andreaspost.gc.cachedb.persistence.entity.AttributeEntity;
import com.andreaspost.gc.cachedb.rest.resource.Attribute;

/**
 * Converts {@link AttributeEntity} to {@link Attribute} and back.
 * 
 * @author Andreas Post
 */
public class AttributesEntityConverter extends AbstractEntityConverter<Attribute, AttributeEntity> {

	@Override
	public Attribute decode(AttributeEntity entity) {
		return new Attribute(entity.getName(), entity.getId());
	}

	@Override
	public AttributeEntity encode(Attribute attribute) {
		return new AttributeEntity(attribute.getName(), attribute.getId());
	}

}
