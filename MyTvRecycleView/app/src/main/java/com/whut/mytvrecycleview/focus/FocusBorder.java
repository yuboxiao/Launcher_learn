package com.whut.mytvrecycleview.focus;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by owen on 2017/7/20.
 */

public interface FocusBorder {

    void setVisible(boolean visible);

    boolean isVisible();

    /**
     * 聚焦
     * @param focusView
     * @param options
     */
    void onFocus(@NonNull View focusView, Options options);
    
    void boundGlobalFocusListener(@NonNull OnFocusCallback callback);
    
    void unBoundGlobalFocusListener();

    interface OnFocusCallback {
        Options onFocus(View oldFocus, View newFocus);
    }
    
    abstract class Options {}
    
    class Builder {
        public final ColorFocusBorder.Builder asColor() {
            return new ColorFocusBorder.Builder();
        }
        
        public final DrawableFocusBorder.Builder asDrawable() {
            return new DrawableFocusBorder.Builder();
        }
    }
    
    class OptionsFactory {
        public static final Options get(float scaleX, float scaleY) {
            return AbsFocusBorder.Options.get(scaleX, scaleY);
        }
        
        public static final Options get(float scaleX, float scaleY, float roundRadius) {
            return ColorFocusBorder.Options.get(scaleX, scaleY, roundRadius);
        }
    }
}
