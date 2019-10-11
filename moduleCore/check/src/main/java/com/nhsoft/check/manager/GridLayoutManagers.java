package com.nhsoft.check.manager;

import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nhsoft.check.viewModel.CheckBaseViewModel;
import com.nhsoft.check.viewModel.RightTwoItemViewModel;

import me.tatarka.bindingcollectionadapter2.LayoutManagers;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class GridLayoutManagers extends LayoutManagers{

    /**
     * A {@link GridLayoutManager} with the given spanCount.
     */
    public static LayoutManagerFactory grids(final int spanCount, final ObservableList<MultiItemViewModel> observableRightList) {
        return new LayoutManagerFactory() {
            @Override
            public RecyclerView.LayoutManager create(RecyclerView recyclerView) {
                GridLayoutManager manager=new GridLayoutManager(recyclerView.getContext(), spanCount);
                manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int i) {
                        MultiItemViewModel itemViewModel=observableRightList.get(i);
                        if (itemViewModel.getItemType().equals(CheckBaseViewModel.MultiRecycleType_Head)){
                            return spanCount;
                        }else if (itemViewModel.getItemType().equals(CheckBaseViewModel.MultiRecycleType_Right1)){
                            return spanCount;
                        }else if (itemViewModel.getItemType().equals(CheckBaseViewModel.MultiRecycleType_Right2)){
                            RightTwoItemViewModel rightTwoItemViewModel= (RightTwoItemViewModel) itemViewModel;
                            if (rightTwoItemViewModel.entity.get().leftTextShow.get()== View.VISIBLE){
                                return 2;
                            }else {
                                return 1;
                            }
                        }else if (itemViewModel.getItemType().equals(CheckBaseViewModel.MultiRecycleType_Right3)){
                            return spanCount;
                        }else if (itemViewModel.getItemType().equals(CheckBaseViewModel.MultiRecycleType_Right4)){
                            return spanCount;
                        }
                        return spanCount;
                    }
                });
                return manager;
            }
        };
    }
}
