package com.nicdahlquist.pngquant;

import java.io.File;

public class PngQuantizer {

    public File quantize(File toCompress) {
        if (toCompress == null) throw new NullPointerException();
        if (!toCompress.exists()) throw new IllegalArgumentException();
        nativeInitialize(toCompress.getAbsolutePath());
        return toCompress;
    }

    static {
        System.loadLibrary("pngquantandroid");
    }

    private static native int nativeInitialize(String input);

}
