package com.shiyue.game.user;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiyue.game.R;
import com.shiyue.game.config.AppConfig;

public class LittleHelper extends Activity implements View.OnClickListener{

    private TextView tapAccount;
    private TextView tapService;
    private TextView tapRaiders;
    private TextView tapPackage;
    private TextView line1,line2,line3,line4;

    private FrameLayout layout_content;
    private FragmentManager fragmentManager;
    private ImageView helper_close;
    private LittleHelperAccountFragment accountFragment;
    private LittleHelperCustomerServiceFragment serviceFragment;
    private LittleHelperRaidersFragment raidersFragment;
    private LittleHelperPackageFragment packageFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.little_helper_activity);
        bindView();

    }

    //绑定对应的按钮
    private void bindView(){
        tapAccount = (TextView) this.findViewById(AppConfig.resourceId(this,"helper_account","id"));
        tapService = (TextView) this.findViewById(AppConfig.resourceId(this,"helper_service","id"));
        tapRaiders = (TextView) this.findViewById(AppConfig.resourceId(this,"helper_raiders","id"));
        tapPackage = (TextView) this.findViewById(AppConfig.resourceId(this,"helper_package","id"));

        line1 = (TextView) this.findViewById(AppConfig.resourceId(this,"line_1","id"));
        line2 = (TextView) this.findViewById(AppConfig.resourceId(this,"line_2","id"));
        line3 = (TextView) this.findViewById(AppConfig.resourceId(this,"line_3","id"));
        line4 = (TextView) this.findViewById(AppConfig.resourceId(this,"line_4","id"));

        helper_close = (ImageView) this.findViewById(AppConfig.resourceId(this,"little_helper_close","id"));
        layout_content = (FrameLayout) this.findViewById(AppConfig.resourceId(this,"little_helper_fragment","id"));

        tapAccount.setOnClickListener(this);
        tapService.setOnClickListener(this);
        tapRaiders.setOnClickListener(this);
        tapPackage.setOnClickListener(this);
        helper_close.setOnClickListener(this);


        setDefaultFragment();

    }

    //重置所有文本的选中状态
    public void selected(){
        tapAccount.setSelected(false);
        tapService.setSelected(false);
        tapRaiders.setSelected(false);
        tapPackage.setSelected(false);

        line1.setSelected(false);
        line2.setSelected(false);
        line3.setSelected(false);
        line4.setSelected(false);
    }
    //设置默认布局
    private void setDefaultFragment(){
        FragmentTransaction transactiona = getFragmentManager().beginTransaction();

        tapAccount.setSelected(true);
        line1.setSelected(true);
        accountFragment = new LittleHelperAccountFragment();
        transactiona.add(AppConfig.resourceId(this,"little_helper_fragment","id"),accountFragment);
        transactiona.commit();
    }

    //隐藏所有的Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if (accountFragment!= null){
            transaction.hide(accountFragment);
        }
        if (serviceFragment!=null){
            transaction.hide(serviceFragment);
        }
        if (raidersFragment!=null){
            transaction.hide(raidersFragment);
        }
        if (packageFragment!=null){
            transaction.hide(packageFragment);
        }
    }

    //

    @Override
    public void onClick(View view) {
        FragmentTransaction transactions = getFragmentManager().beginTransaction();
        hideAllFragment(transactions);
        if (view.getId()==AppConfig.resourceId(this,"helper_account","id"))
        {

            selected();
            tapAccount.setSelected(true);
            line1.setSelected(true);
            if (accountFragment==null){
                accountFragment = new LittleHelperAccountFragment();

                transactions.add(AppConfig.resourceId(this,"little_helper_fragment","id"),accountFragment);
            }else {
                transactions.show(accountFragment);
            }


        }else if (view.getId()==AppConfig.resourceId(this,"helper_service","id"))
        {

            selected();
            tapService.setSelected(true);
            line2.setSelected(true);
            if (serviceFragment == null){
                serviceFragment = new LittleHelperCustomerServiceFragment();
                transactions.add(AppConfig.resourceId(this,"little_helper_fragment","id"),serviceFragment);
            }else {
                transactions.show(serviceFragment);
            }


        }else if (view.getId() == AppConfig.resourceId(this,"helper_raiders","id"))
        {

            selected();
            tapRaiders.setSelected(true);
            line3.setSelected(true);
            if (raidersFragment ==null){
                raidersFragment = new LittleHelperRaidersFragment();
                transactions.add(AppConfig.resourceId(this,"little_helper_fragment","id"),raidersFragment);
            }else {
                transactions.show(raidersFragment);
            }


        }else if (view.getId()==AppConfig.resourceId(this,"helper_package","id"))
        {

            selected();
            tapPackage.setSelected(true);
            line4.setSelected(true);
            if (packageFragment==null){
                packageFragment = new LittleHelperPackageFragment();
                transactions.add(AppConfig.resourceId(this,"little_helper_fragment","id"),packageFragment);
            }else {
                transactions.show(packageFragment);
            }


        }else if (view.getId()==AppConfig.resourceId(this,"little_helper_close","id"))
        {

            finish();

        }

        transactions.commit();

    }
}
