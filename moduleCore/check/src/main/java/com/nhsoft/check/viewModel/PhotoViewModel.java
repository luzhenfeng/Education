package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.view.View;

import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.takephoto.model.TResult;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.entity.PhotoEntity;
import com.nhsoft.check.entity.PhotoItemEntity;
import com.nhsoft.check.message.ConstantMessage;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * Created by lzf on 2019/10/18.
 * Describe:
 */

public class PhotoViewModel extends BasePopupViewModel<Repository>{

    public ObservableField<PhotoEntity> entity=new ObservableField<>();

    //真实几张照片
    public ObservableInt realityNum=new ObservableInt(0);
    //最多选几张照片
    public ObservableInt limit=new ObservableInt(9);

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        public SingleLiveEvent onClickImage = new SingleLiveEvent<>();
    }


    public PhotoViewModel(@NonNull Application application) {
        super(application);
        model= Injection.provideDemoRepository();
        entity.set(new PhotoEntity());
        itemBinding();
        bindingCommand();
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        entity.get().onClick=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                model.savePhotos(setPhotoPath());
                Messenger.getDefault().send(realityNum.get(),ConstantMessage.TOKEN_PHOTOVIEWMODEL_PHOTOPATHS);
                AppManager.getAppManager().currentActivity().finish();
            }
        });
    }

    public void itemBinding() {
        entity.get().itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_photo);
    }

    /**
     * 设置数据
     */
    public void setData(){
        addDefaultPic();
        String photos=model.getPhotos();
        if (photos.equals("")){
            return;
        }
        String[] paths=photos.split(",");
        for (String s:paths){
            addPhotoBean(s);
        }
    }

    /**
     * 添加默认的
     */
    public void addDefaultPic() {
        PhotoItemEntity photoItemEntity=new PhotoItemEntity();
        photoItemEntity.showError.set(false);
        PhotoItemViewModel photoItemViewModel=new PhotoItemViewModel(this,photoItemEntity);
        entity.get().observableList.add(photoItemViewModel);
    }


    /**
     * 添加图片
     * @param path
     */
    public void addPhotoBean(String path) {
        PhotoItemEntity photoItemEntity=new PhotoItemEntity();
        photoItemEntity.imagePath.set(path);
        photoItemEntity.showError.set(true);
        PhotoItemViewModel photoItemViewModel=new PhotoItemViewModel(this,photoItemEntity);
        entity.get().observableList.add(entity.get().observableList.size()-1,photoItemViewModel);
        realityNum.set(entity.get().observableList.size()-1);
        if (realityNum.get()==limit.get()){
            entity.get().observableList.remove(limit.get());
        }
    }

    /**
     * 拍照之后获得的图片
     * @param result
     */
    public void setAlbumPic(TResult result) {
        for (int i = 0; i < result.getImages().size(); i++) {
            addPhotoBean(result.getImages().get(i).getCompressPath());
        }
    }

    /**
     * 移除条目
     */
    public void removePosition(int pos){
        entity.get().observableList.remove(pos);
        realityNum.set(realityNum.get()-1);
    }

    /**
     * 获取条目下标
     *
     * @param photoItemViewModel
     * @return
     */
    public int getItemPosition(PhotoItemViewModel photoItemViewModel) {
        return entity.get().observableList.indexOf(photoItemViewModel);
    }


    /**
     * 组合全部图片的路径并保存
     * @return
     */
    public String setPhotoPath(){
        String photo="";
        List<PhotoItemViewModel> photoItemViewModelList=entity.get().observableList.subList(0,realityNum.get());
        for (PhotoItemViewModel photoItemViewModel:photoItemViewModelList){
            if (photoItemViewModelList.indexOf(photoItemViewModel)==realityNum.get()-1){
                photo+=photoItemViewModel.entity.get().imagePath.get();
            }else {
                photo+=photoItemViewModel.entity.get().imagePath.get()+",";
            }
        }
        return photo;
    }
}
