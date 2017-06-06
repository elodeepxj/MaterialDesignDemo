package com.jokerpeng.demo.materialdesigndemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CoordinatorLayout coordinatorLayout;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private TextInputLayout textInputLayout;
    private TextInputEditText textInputEditText;
    private FloatingActionButton floatingActionButton;
    private boolean floatingButtonIsShow;
    private TabLayout tablayou;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAction();
    }

    private void initData() {
        setFloatingActionButton();
        setToolBar();
        setTabLayout();
    }

    private void setTabLayout() {
        tablayou.addTab(tablayou.newTab().setText("页面一"));
        tablayou.addTab(tablayou.newTab().setText("页面二"));
        tablayou.addTab(tablayou.newTab().setText("页面三"));

        initViewPagerData();
        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        viewPager.setAdapter(adapter);
//        tablayou.setupWithViewPager(viewPager);
//        tablayou.setTabsFromPagerAdapter(adapter);

    }
    private List<View> mList_viewPager;
    private void initViewPagerData() {
        mList_viewPager = new ArrayList<>();

        for(int i = 0;i<tablayou.getTabCount();i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(R.mipmap.ic_launcher);
            mList_viewPager.add(imageView);
        }

        list = new ArrayList<>();
        for(int i = 0;i<30;i++){
            list.add("第"+i+"条数据");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapter(list));
    }

    private void setToolBar() {
        //取消toolbar的title内容，默认为label
        toolbar.setTitle(" ");
        //设置toolbar为actionbar
        setSupportActionBar(toolbar);
        //显示toolbar的home键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置actionbar的toggle按钮,把toolbar和drawerLayout连接起来
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        //drawerLayou添加打开监听为toggle按钮
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //同步更新
        actionBarDrawerToggle.syncState();
    }

    private void setFloatingActionButton() {
        floatingButtonIsShow = false;
        floatingActionButton.setVisibility(View.GONE);
    }



    private void initAction() {
        //设置navigation子菜单选择监听
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initView() {
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);
        navigationView = (NavigationView) findViewById(R.id.nv);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floating_action_button);
        viewPager = (ViewPager) findViewById(R.id.vp);
        tablayou = (TabLayout) findViewById(R.id.tab);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.snakebar:
                Snackbar.make(coordinatorLayout,"选项一",Snackbar.LENGTH_LONG).setAction("选项一", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭drawerLayout
                        Toast.makeText(MainActivity.this,"你点SnackBar一下",Toast.LENGTH_LONG).show();
                    }
                }).show();
                break;
            case R.id.text_input_layout:
                new TextInputLayoutDialog(MainActivity.this).show();
                break;
            case R.id.floating_action_button:
                if(!floatingButtonIsShow){
                    floatingActionButton.setVisibility(View.VISIBLE);
                    floatingButtonIsShow =true;
                }else{
                    floatingActionButton.setVisibility(View.GONE);
                    floatingButtonIsShow = false;
                }
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
        return false;
    }

    public class TextInputLayoutDialog extends Dialog{
        Context context;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View view = LayoutInflater.from(context).inflate(R.layout.layout_textinput_dialog,null);
            textInputLayout = (TextInputLayout) view.findViewById(R.id.input_layout);
            textInputEditText = (TextInputEditText) view.findViewById(R.id.input_et);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCanceledOnTouchOutside(true);
            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.length()>10){
                        textInputLayout.setError("输入的内容太多了");
                    }
                }
            });
            setContentView(view);
        }

        public TextInputLayoutDialog(@NonNull Context context) {
            super(context);
            initDialogData(context);
        }

        private void initDialogData(Context context) {
            this.context = context;
        }

    }

    public class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return mList_viewPager.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList_viewPager.get(position));
            return mList_viewPager.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList_viewPager.get(position));
        }
    }
}
