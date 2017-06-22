package test.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crashutils.CrachCallback;
import com.zxy.recovery.core.Recovery;

import java.util.ArrayList;

/**
 * Created by thinkpad on 2017/6/21.
 */

public class TwoActivity extends Activity {
    public EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two);
        CrachCallback crachCallback=(CrachCallback)(Recovery.getInstance().getmCallback());
        crachCallback.setAccount("假装客户");
        Button bt= (Button) findViewById(R.id.bt);
        Button bt2= (Button) findViewById(R.id.bt2);
        Button bt3= (Button) findViewById(R.id.bt3);
        EditText edt= (EditText) findViewById(R.id.edt);
        edt.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
//        设置字符过滤
        edt.setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals(".") && dest.toString().length() == 0){
                    return "0.";
                }
                if(dest.toString().contains(".")){
                    int index = dest.toString().indexOf(".");
                    int mlength = dest.toString().substring(index).length();
                    if(mlength == 3){
                        return "";
                    }
                }
                return null;
            }
        }});

        bt.setText("点击崩溃");
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i=1/0;
            }
        });

        bt2.setVisibility(View.VISIBLE);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setEnabled(true);
            }
        });

        bt3.setVisibility(View.VISIBLE);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList=new ArrayList<String>();
                arrayList.add("1");
                arrayList.get(2);
            }
        });
    }

}
