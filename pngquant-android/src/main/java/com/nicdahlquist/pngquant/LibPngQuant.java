package com.nicdahlquist.pngquant;

public class LibPngQuant {

    public boolean pngQuantFile(String inputFilename, String outputFilename) {

        return nativePngQuantFile(inputFilename, outputFilename);
    }

    static {
        System.loadLibrary("pngquantandroid");
    }

    private static native boolean nativePngQuantFile(String inputFilename, String outputFilename);

}
