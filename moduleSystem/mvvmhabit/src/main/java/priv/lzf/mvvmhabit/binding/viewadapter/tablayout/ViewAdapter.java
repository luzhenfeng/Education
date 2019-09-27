package priv.lzf.mvvmhabit.binding.viewadapter.tablayout;

import android.databinding.BindingAdapter;
import android.support.design.widget.TabLayout;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/9/27.
 * Describe:
 */

public class ViewAdapter {
    //选中条目变换监听
    @BindingAdapter(value = {"onTabSelectedCommand", "onTabUnselectedCommand", "onTabReselectedCommand"}, requireAll = false)
    public static void addOnTabSelectedCommand(TabLayout tabLayout,
                                               final BindingCommand<TabLayout.Tab> onTabSelectedCommand,
                                               final BindingCommand<TabLayout.Tab> onTabUnselectedCommand,
                                               final BindingCommand<TabLayout.Tab> onTabReselectedCommand) {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (onTabSelectedCommand != null) {
                    onTabSelectedCommand.execute(tab);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (onTabUnselectedCommand != null) {
                    onTabUnselectedCommand.execute(tab);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (onTabReselectedCommand != null) {
                    onTabReselectedCommand.execute(tab);
                }
            }
        });
    }
}
