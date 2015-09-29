package com.example.nikunj.virtualwardrobe;

public class ConvertRGBtoLab {

    public float var_R, var_G, var_B, X, Y, Z, var_X, var_Y, var_Z, CIE_L, CIE_a, CIE_b;
  //  public float CIE_Lab[] = null;

    private final float COMPONENT_A =  0.055f;
    private final float COMPONENT_B =  1.055f;
    private final float COMPONENT_C =  2.4f;
    private final float COMPONENT_D =  12.92f;
    private final float COMPONENT_E =  100f;
    private final float COMPONENT_F =  7.787f;
    private final float COMPONENT_G =  0.1379310f;
    private final float COMPONENT_H =  0.3333333f;

    private final float REF_X = 95.047f;
    private final float REF_Y = 100f;
    private final float REF_Z = 108.883f;

    public float delE;

    int R,G,B;

    public ConvertRGBtoLab(int R,int G,int B){
        this.R=R;
        this.B=B;
        this.G=G;
    }

    public CIE_LAB conversion() {
        ////RGB to XYZ
        var_R = ((float)R / 255f);       //R from 0 to 255
        var_G = ((float)G / 255f);        //G from 0 to 255
        var_B = ((float)B / 255f);        //B from 0 to 255



        if (var_R > 0.04045f) {
            var_R = (float) Math.pow(((var_R + COMPONENT_A) / COMPONENT_B), COMPONENT_C);

        }
        else {
            var_R = var_R / COMPONENT_D;

        }
        if (var_G > 0.04045f) {
            var_G = (float) Math.pow(((var_G + COMPONENT_A) / COMPONENT_B), COMPONENT_C);

        }
        else {
            var_G = var_G / COMPONENT_D;

        }
        if (var_B > 0.04045f) {
            var_B = (float) Math.pow(((var_B + COMPONENT_A) / COMPONENT_B), COMPONENT_C);

        }
        else {
            var_B = var_B / COMPONENT_D;

        }


        var_R =  var_R * COMPONENT_E;
        var_G =  var_G * COMPONENT_E;
        var_B =  var_B * COMPONENT_E;


        //Observer. = 2°, Illuminant = D65
        X = (float) ((var_R * 0.4124f) + (var_G * 0.3576f) + (var_B * 0.1805f));
        Y = (float) ((var_R * 0.2126f) + (var_G * 0.7152f) + (var_B * 0.0722f));
        Z = (float) ((var_R * 0.0193f) + (var_G * 0.1192f) + (var_B * 0.9505f));


// XYZ to CIELab

        var_X = X / REF_X;      //REF_X =  95.047   Observer= 2°, Illuminant= D65
        var_Y = Y / REF_Y;          //REF_Y = 100.000
        var_Z = Z / REF_Z;          //REF_Z = 108.883


        if (var_X > 0.008856f){
            var_X = (float) Math.pow(var_X, COMPONENT_H);
        }
        else {
            var_X = (float) ((COMPONENT_F * var_X) + COMPONENT_G);
        }
        if (var_Y > 0.008856f){
            var_Y = (float) Math.pow(var_Y, COMPONENT_H);
        }
        else var_Y = (float) ((COMPONENT_F * var_Y) + COMPONENT_G);
        if (var_Z > 0.008856f) var_Z = (float) Math.pow(var_Z, COMPONENT_H);
        else var_Z = (float) ((COMPONENT_F * var_Z) + COMPONENT_G);

        CIE_L = ((116f * var_Y) - 16f);
        CIE_a = (500f * (var_X - var_Y));
        CIE_b = (200f * (var_Y - var_Z));

        CIE_LAB lab1= new CIE_LAB();
        lab1.L= CIE_L;
        lab1.A= CIE_a;
        lab1.B= CIE_b;

        //CIE_Lab = new float[]{CIE_L, CIE_a, CIE_b};

//        DeltaECalculator deltaECalculator= new DeltaECalculator(CIE_L,CIE_a,CIE_b);
//        delE = deltaECalculator.CIEDeltaE2000();

        return lab1;

    }



}

////RGB to XYZ
//
//    var_R = ( R / 255 )        //R from 0 to 255
//    var_G = ( G / 255 )        //G from 0 to 255
//    var_B = ( B / 255 )        //B from 0 to 255
//
//            if ( var_R > 0.04045 ) var_R = ( ( var_R + 0.055 ) / 1.055 ) ^ 2.4
//            else                   var_R = var_R / 12.92
//            if ( var_G > 0.04045 ) var_G = ( ( var_G + 0.055 ) / 1.055 ) ^ 2.4
//            else                   var_G = var_G / 12.92
//            if ( var_B > 0.04045 ) var_B = ( ( var_B + 0.055 ) / 1.055 ) ^ 2.4
//            else                   var_B = var_B / 12.92
//Math.pow(Math.pow(distance[1],2)+Math.pow(coeffs[3],2),0.5);
//    var_R = var_R * 100;
//    var_G = var_G * 100
//    var_B = var_B * 100
//
////Observer. = 2°, Illuminant = D65
//    X = var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805
//    Y = var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722
//    Z = var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505
//
//
//// XYZ to CIELab
//
//    var_X = X / REF_X          //REF_X =  95.047   Observer= 2°, Illuminant= D65
//            var_Y = Y / REF_Y          //REF_Y = 100.000
//    var_Z = Z / REF_Z          //REF_Z = 108.883
//
//    if ( var_X > 0.008856 ) var_X = var_X ^ ( 1/3 )
//            else                    var_X = ( 7.787 * var_X ) + ( 16 / 116 )
//            if ( var_Y > 0.008856 ) var_Y = var_Y ^ ( 1/3 )
//            else                    var_Y = ( 7.787 * var_Y ) + ( 16 / 116 )
//            if ( var_Z > 0.008856 ) var_Z = var_Z ^ ( 1/3 )
//            else                    var_Z = ( 7.787 * var_Z ) + ( 16 / 116 )
//
//    CIE-L* = ( 116 * var_Y ) - 16
//    CIE-a* = 500 * ( var_X - var_Y )
//    CIE-b* = 200 * ( var_Y - var_Z )
//
//            return (CIE-L*,CIE-a*,CIE-b*);


