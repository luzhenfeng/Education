package priv.lzf.mvvmhabit.binding.viewadapter.radiogroup;

import android.databinding.BindingAdapter;
import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by goldze on 2017/6/18.
 */
public class ViewAdapter {
    @BindingAdapter(value = {"onCheckedChangedCommand"}, requireAll = false)
    public static void onCheckedChangedCommand(final RadioGroup radioGroup, final BindingCommand<String> bindingCommand) {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (radioButton!=null)
                    bindingCommand.execute(radioButton.getText().toString());
            }
        });
    }

    @BindingAdapter(value = {"clearCheck"}, requireAll = false)
    public static void clearCheck(final RadioGroup radioGroup,boolean clearCheck) {
        if (clearCheck)
            radioGroup.clearCheck();
    }


}
