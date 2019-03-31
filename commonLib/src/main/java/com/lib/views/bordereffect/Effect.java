package com.lib.views.bordereffect;

import android.view.View;

public interface Effect {
    void onFocusChanged(View target, View oldFocus, View newFocus);

    void onScrollChanged(View target, View attachView);

    void onLayout(View target, View attachView);

    void onTouchModeChanged(View target, View attachView, boolean isInTouchMode);

    void onAttach(View target, View attachView);

    void OnDetach(View targe, View view);

    <T> T toEffect(Class<T> t);
}
