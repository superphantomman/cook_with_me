package com.superphantomman.cook_with_me.util;


//TODO resolve problem with float
public enum MeasurementType {
    GRAM(
            "GRAM", 1,
            "DECAGRAM", 10),
    DECAGRAM(
            "GRAM", 10,
            "KILOGRAMS", 100
    ),
    KILOGRAMS(
            "DECAGRAM", 10,
            "KILOGRAMS", 1),
    MILLILITRE(
            "MILLILITRE", 1,
            "LITRE", 1000
    ),

    LITER(
            "MILLILITRE", 100,
            "LITRE", 1
    );
    final String lowerType;
    final float lowerVal;
    final String upperType;
    final float upperVal;

    MeasurementType(
            final String lowerType, final int lowerVal,
            final String upperType, final int upperVal
    ) {
        this.lowerType = lowerType;
        this.lowerVal = lowerVal;
        this.upperType = upperType;
        this.upperVal = upperVal;
    }

    public MeasurementType convertToUpper(){
        return valueOf(upperType);
    }
    public MeasurementType convertToLower(){
        return valueOf(lowerType);
    }

    public float convertValToUpper(float value){
        return value * upperVal;
    }
    public float convertValToLover(float value){
        return  value / lowerVal;
    }

}
