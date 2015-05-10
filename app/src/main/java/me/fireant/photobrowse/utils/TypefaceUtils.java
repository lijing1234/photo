package me.fireant.photobrowse.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by 火蚁 on 15/5/10.
 */
public class TypefaceUtils {

    public static Typeface getTypefaceForAssets(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
    }

    public static void setSemTextIcon(TextView textView) {
        textView.setTypeface(getTypefaceForAssets(textView.getContext()));
    }
}
