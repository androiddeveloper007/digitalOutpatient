package com.lib.util;


import com.lib.dialog.BaseDialog;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class DialogManagerUtil {
    private static DialogManagerUtil instance;

    public List<SoftReference<BaseDialog>> dialoglist;

    public DialogManagerUtil() {
        dialoglist = new ArrayList<>();
    }

    public static DialogManagerUtil getInstance(){
        if(instance == null){
            synchronized (DialogManagerUtil.class){
                if(instance == null){
                    instance = new DialogManagerUtil();
                }
            }
        }
        return instance;
    }
    public void add(BaseDialog dialog){
        SoftReference<BaseDialog> dialogWR = new SoftReference<BaseDialog>(dialog);
        dialoglist.add(dialogWR);
    }
    public void remove(BaseDialog dialog){
        SoftReference<BaseDialog> dialogWR = new SoftReference<BaseDialog>(dialog);
        dialoglist.remove(dialogWR);
    }

    public void closeAll(){
        if(dialoglist==null||dialoglist.size() == 0)
            return;
        for(SoftReference<BaseDialog> dialog:dialoglist){
            if(dialog.get()!=null&&dialog.get().isShowing()){
                dialog.get().dismiss();
            }
        }
    }
}
