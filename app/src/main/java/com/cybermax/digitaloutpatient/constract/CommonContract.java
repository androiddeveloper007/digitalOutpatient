package com.cybermax.digitaloutpatient.constract;

public interface CommonContract {

    interface View<T> {
        void onLoadSuccess(T object);

        void onLoadFail(String str);
    }

    interface BaseView {
        void makeShortToast(String text);
    }
}
