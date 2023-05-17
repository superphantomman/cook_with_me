package com.superphantomman.cook_with_me.util;

import java.util.EnumSet;

import static com.superphantomman.cook_with_me.util.MeasurementType.*;

public interface Groups {
    EnumSet<MeasurementType> LITERS = EnumSet.of(LITER, MILLILITRE);
    EnumSet<MeasurementType> GRAMS = EnumSet.of(GRAM, DECAGRAM, KILOGRAMS);
}
