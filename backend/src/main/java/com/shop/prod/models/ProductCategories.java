package com.shop.prod.models;

public enum ProductCategories {
    LIQUIDS("LIQUIDS"),
    VAPES("VAPES"),
    CONSUMABLES("CONSUMABLES"),
    DISPOSABLES("DISPOSABLES"),
    OTHER("OTHER");

    final private String category;

    ProductCategories(String category){
        this.category = category;
    }

    @Override
    public String toString(){
        return category;
    }
}
