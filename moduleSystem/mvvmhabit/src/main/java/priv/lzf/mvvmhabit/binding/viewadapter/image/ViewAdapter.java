package priv.lzf.mvvmhabit.binding.viewadapter.image;


import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"url", "placeholderRes","circle"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes,boolean circle) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext().getApplicationContext())
                    .load(url)
                    .apply(circle?new RequestOptions().placeholder(placeholderRes).circleCropTransform():new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }
    }

//    @BindingAdapter(value = {"imgRes", "placeholderRes"}, requireAll = false)
//    public static void setImageRes(ImageView imageView, int placeholderRes) {
//        //使用Glide框架加载图片
//        Glide.with(imageView.getContext())
//                .load(new RequestOptions().placeholder(placeholderRes))
//                .into(imageView);
//
//    }
}

