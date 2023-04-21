package com.superphantomman.cook_with_me.util;

public class DecimalValue {
    private int unit;
    private int scale;
    private int rest;
    //TODO add exception
    DecimalValue(String value){
        String[] va = value.split("\\.");
        int unit = Integer.parseInt(va[0]);
        int scale = (int)Math.pow(10.d,  va[1].length());
        int rest = Integer.parseInt(va[1]);

    }
    public void add(DecimalValue other){
        this.unit += other.unit + ( other.rest / scale );
        this.rest = ( this.rest + other.rest ) %  scale;
    }
    public void minus(DecimalValue other){
        int v = ( this.rest < other.rest ? 1 : 0 );
        this.unit -= other.unit - v ;
        this.rest = ( this.rest - other.rest ) + v * scale ;

    }


}
