package com.shop.prod.models;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductCategoryConverter implements AttributeConverter<ProductCategories, String> {

    @Override
    public String convertToDatabaseColumn(ProductCategories category) {
        if (category == null) {
            return null;
        }
        return category.toString();
    }

    @Override
    public ProductCategories convertToEntityAttribute(String category) {
        if (category == null) {
            return null;
        }
        return ProductCategories.valueOf(category);
    }
}
