package com.nhsoft.check.manager;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.tatarka.bindingcollectionadapter2.LayoutManagers;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class GridLayoutManagers extends LayoutManagers{

    /**
     * A {@link GridLayoutManager} with the given spanCount.
     */
    public static LayoutManagerFactory grids(final int spanCount, final int startPos,final int endPos) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                GridLayoutManager manager=new GridLayoutManager(recyclerView.getContext(), spanCount);
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int i) {
                        if (i<startPos){
                            return spanCount;
                        }else if (i==startPos){
                            return 2;
                        }else if (i<endPos){
                            return 1;
                        }
                        return spanCount;
                    }
                });
                return manager;
            }
        };
    }
}
