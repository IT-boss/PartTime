package phxg.com.parttimejob.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;

import phxg.com.parttimejob.R;


/**
 * Created on 2018/2/1.
 */

public class LoadingDialog extends Dialog {

    private ProgressBar bar;

    public LoadingDialog(Context context) {
        super(context, R.style.ProgressDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_dialog);
        bar = (ProgressBar) findViewById(R.id.battery_view);
    }

}
