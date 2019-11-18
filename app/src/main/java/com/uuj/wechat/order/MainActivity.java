package com.uuj.wechat.order;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uuj.wechat.order.adapter.AdapterLeftMenu;
import com.uuj.wechat.order.adapter.AdapterRightDish;
import com.uuj.wechat.order.custom_view.RxFakeAddImageView;
import com.uuj.wechat.order.custom_view.RxPointFTypeEvaluator;
import com.uuj.wechat.order.interfaces.ShopCartInterface;
import com.uuj.wechat.order.model.ModelDish;
import com.uuj.wechat.order.model.ModelDishMenu;
import com.uuj.wechat.order.model.ModelShopCart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ShopCartInterface, AdapterLeftMenu.onItemSelectedListener {

    @BindView(R.id.shopping_cart_total_tv)
    TextView shoppingCartTotalTv;// 购物车总价格
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout shoppingCartBottom;// 购物车背景栏
    @BindView(R.id.left_menu)
    RecyclerView leftMenu;// 左边菜单
    @BindView(R.id.right_menu)
    RecyclerView rightMenu;// 右边菜单
    @BindView(R.id.right_menu_tv)
    TextView rightMenuTv;// 右边菜单小标题
    @BindView(R.id.right_menu_item)
    LinearLayout rightMenuItem;//右侧菜单栏最上面的菜单
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;// 购物车
    @BindView(R.id.shopping_cart_layout)
    FrameLayout shoppingCartLayout;// 购物车背景
    @BindView(R.id.shopping_cart_total_num)
    TextView shoppingCartTotalNum;// 购物车总数量
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    private ModelShopCart mModelShopCart;
    private ArrayList<ModelDishMenu> mModelDishMenuList;//数据源
    private ModelDishMenu headMenu;
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private AdapterLeftMenu leftAdapter;
    private AdapterRightDish rightAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
        initAdapter();
    }



    private void initData() {
        mModelShopCart = new ModelShopCart();
        mModelDishMenuList = new ArrayList<>();
        ArrayList<ModelDish> dishs1 = new ArrayList<>();
        dishs1.add(new ModelDish("面包", 1.0, 10));
        dishs1.add(new ModelDish("蛋挞", 1.0, 10));
        dishs1.add(new ModelDish("牛奶", 1.0, 10));
        dishs1.add(new ModelDish("肠粉", 1.0, 10));
        dishs1.add(new ModelDish("绿茶饼", 1.0, 10));
        dishs1.add(new ModelDish("花卷", 1.0, 10));
        dishs1.add(new ModelDish("包子", 1.0, 10));
        ModelDishMenu breakfast = new ModelDishMenu("早点", dishs1);

        ArrayList<ModelDish> dishs2 = new ArrayList<>();
        dishs2.add(new ModelDish("粥", 1.0, 10));
        dishs2.add(new ModelDish("炒饭", 1.0, 10));
        dishs2.add(new ModelDish("炒米粉", 1.0, 10));
        dishs2.add(new ModelDish("炒粿条", 1.0, 10));
        dishs2.add(new ModelDish("炒牛河", 1.0, 10));
        dishs2.add(new ModelDish("炒菜", 1.0, 10));
        ModelDishMenu launch = new ModelDishMenu("午餐", dishs2);

        ArrayList<ModelDish> dishs3 = new ArrayList<>();
        dishs3.add(new ModelDish("淋菜", 1.0, 10));
        dishs3.add(new ModelDish("川菜", 1.0, 10));
        dishs3.add(new ModelDish("湘菜", 1.0, 10));
        dishs3.add(new ModelDish("粤菜", 1.0, 10));
        dishs3.add(new ModelDish("赣菜", 1.0, 10));
        dishs3.add(new ModelDish("东北菜", 1.0, 10));
        ModelDishMenu evening = new ModelDishMenu("晚餐", dishs3);

        ArrayList<ModelDish> dishs4 = new ArrayList<>();
        dishs4.add(new ModelDish("淋菜", 1.0, 10));
        dishs4.add(new ModelDish("川菜", 1.0, 10));
        dishs4.add(new ModelDish("湘菜", 1.0, 10));
        dishs4.add(new ModelDish("湘菜", 1.0, 10));
        dishs4.add(new ModelDish("湘菜1", 1.0, 10));
        dishs4.add(new ModelDish("湘菜2", 1.0, 10));
        dishs4.add(new ModelDish("湘菜3", 1.0, 10));
        dishs4.add(new ModelDish("湘菜4", 1.0, 10));
        dishs4.add(new ModelDish("湘菜5", 1.0, 10));
        dishs4.add(new ModelDish("湘菜6", 1.0, 10));
        dishs4.add(new ModelDish("湘菜7", 1.0, 10));
        dishs4.add(new ModelDish("湘菜8", 1.0, 10));
        dishs4.add(new ModelDish("粤菜", 1.0, 10));
        dishs4.add(new ModelDish("赣菜", 1.0, 10));
        dishs4.add(new ModelDish("东北菜", 1.0, 10));
        ModelDishMenu menu1 = new ModelDishMenu("夜宵", dishs4);

        mModelDishMenuList.add(breakfast);
        mModelDishMenuList.add(launch);
        mModelDishMenuList.add(evening);
        mModelDishMenuList.add(menu1);
    }

    private void initView() {
        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        rightMenu.setLayoutManager(new LinearLayoutManager(this));

        rightMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;
                if (dy > 0) {
                    underView = rightMenu.findChildViewUnder(rightMenuItem.getX(), rightMenuItem.getMeasuredHeight() + 1);
                } else {
                    underView = rightMenu.findChildViewUnder(rightMenuItem.getX(), 0);
                }
                if (underView != null && underView.getContentDescription() != null) {
                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    ModelDishMenu menu = rightAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getMenuName().equals(headMenu.getMenuName())) {
                        if (dy > 0 && rightMenuItem.getTranslationY() <= 1 && rightMenuItem.getTranslationY() >= -1 * rightMenuItem.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - rightMenuItem.getMeasuredHeight();
                            rightMenuItem.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+rightMenuItem.getTranslationY()+"   "+rightMenuItem.getBottom()+"  -  "+rightMenuItem.getMeasuredHeight() );
                        } else if (dy < 0 && rightMenuItem.getTranslationY() <= 0 && !leftClickType) {
                            rightMenuTv.setText(menu.getMenuName());
                            int dealtY = underView.getBottom() - rightMenuItem.getMeasuredHeight();
                            rightMenuItem.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+rightMenuItem.getTranslationY()+"   "+rightMenuItem.getBottom()+"  -  "+rightMenuItem.getMeasuredHeight() );
                        } else {
                            rightMenuItem.setTranslationY(0);
                            headMenu = menu;
                            rightMenuTv.setText(headMenu.getMenuName());
                            for (int i = 0; i < mModelDishMenuList.size(); i++) {
                                if (mModelDishMenuList.get(i) == headMenu) {
                                    leftAdapter.setSelectedNum(i);
                                    break;
                                }
                            }
                            if (leftClickType) {
                                leftClickType = false;
                            }
                            //Log.e(TAG, "onScrolled: " + menu.getMenuName());
                        }
                    }
                }
            }
        });

        shoppingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCart(view);
            }
        });
    }


    private void initAdapter() {
        leftAdapter = new AdapterLeftMenu(this, mModelDishMenuList);
        rightAdapter = new AdapterRightDish(this, mModelDishMenuList, mModelShopCart);
        rightMenu.setAdapter(rightAdapter);
        leftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);
        rightAdapter.setShopCartInterface(this);
        initHeadView();
    }

    private void initHeadView() {
        headMenu = rightAdapter.getMenuOfMenuByPosition(0);
        rightMenuItem.setContentDescription("0");
        rightMenuTv.setText(headMenu.getMenuName());
    }



    private void showHeadView() {
        rightMenuItem.setTranslationY(0);
        View underView = rightMenu.findChildViewUnder(rightMenuItem.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            ModelDishMenu menu = rightAdapter.getMenuOfMenuByPosition(position + 1);
            headMenu = menu;
            rightMenuTv.setText(headMenu.getMenuName());
            for (int i = 0; i < mModelDishMenuList.size(); i++) {
                if (mModelDishMenuList.get(i) == headMenu) {
                    leftAdapter.setSelectedNum(i);
                    break;
                }
            }
        }
    }


    @Override
    public void add(View view, int postion) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCart.getLocationInWindow(cartLocation);
        rightMenu.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final RxFakeAddImageView rxFakeAddImageView = new RxFakeAddImageView(this);
        mainLayout.addView(rxFakeAddImageView);
        rxFakeAddImageView.setImageResource(R.mipmap.ic_add_circle_blue_700_36dp);
        rxFakeAddImageView.getLayoutParams().width = 50;
        rxFakeAddImageView.getLayoutParams().height = 50;
        rxFakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(rxFakeAddImageView, "mPointF",
                new RxPointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                rxFakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rxFakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(rxFakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCart, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCart, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();

        showTotalPrice();
    }

    @Override
    public void remove(View view, int position) {
        showTotalPrice();
    }

    private void showTotalPrice() {
        if (mModelShopCart != null && mModelShopCart.getShoppingTotalPrice() > 0) {
            shoppingCartTotalTv.setVisibility(View.VISIBLE);
            shoppingCartTotalTv.setText("¥ " + mModelShopCart.getShoppingTotalPrice());
            shoppingCartTotalNum.setVisibility(View.VISIBLE);
            shoppingCartTotalNum.setText("" + mModelShopCart.getShoppingAccount());
        } else {
            shoppingCartTotalTv.setVisibility(View.GONE);
            shoppingCartTotalNum.setVisibility(View.GONE);
        }
    }


    @Override
    public void onLeftItemSelected(int position, ModelDishMenu menu) {
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += mModelDishMenuList.get(i).getModelDishList().size() + 1;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) rightMenu.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leftAdapter.removeItemSelectedListener(this);
    }
}
