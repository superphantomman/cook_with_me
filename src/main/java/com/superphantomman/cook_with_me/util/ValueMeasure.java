package com.superphantomman.cook_with_me.util;

public class ValueMeasure {
    private MeasurementType mt;
    private float value;

    ValueMeasure(MeasurementType mt, float value) {

    }

    public ValueMeasure getUpper() {
        return new ValueMeasure(mt.convertToUpper(), mt.convertValToUpper(value) );
    }

    public ValueMeasure getLower() {
        return new ValueMeasure(mt.convertToLower(), mt.convertValToLover(value));
    }
}

