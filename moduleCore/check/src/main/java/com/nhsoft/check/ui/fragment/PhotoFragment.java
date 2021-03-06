package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzf.takephoto.model.TResult;
import com.nhsoft.base.BR;
import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentPhotoBinding;
import com.nhsoft.check.viewModel.PhotoViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends BaseTokePhotoFragment<FragmentPhotoBinding, PhotoViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_photo;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        viewModel.setData();
//        binding.
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.onClickImage.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                showPopup(viewModel.limit.get() - viewModel.realityNum.get());
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        if (isCamera) {
            String path = result.getImage().getCompressPath();
            viewModel.addPhotoBean(path);
        } else {
            viewModel.setAlbumPic(result);
        }
    }


}
