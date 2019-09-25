package com.nhsoft.check;

import android.app.Application;

import com.nhsoft.base.base.IModuleInit;

/**
 * Created by lzf on 2019/9/23.
 * Describe:
 */

public class CheckModuleInit implements IModuleInit {
    @Override
    public boolean onInitAhead(Application application) {
        return false;
    }

    @Override
    public boolean onInitLow(Application application) {
        return false;
    }
}
