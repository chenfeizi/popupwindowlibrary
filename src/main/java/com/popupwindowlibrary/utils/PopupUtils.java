package com.popupwindowlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.popupwindowlibrary.R;
import com.popupwindowlibrary.adapter.CouponsPopWindowAdapter;
import com.popupwindowlibrary.bean.PopWindowInfo;
import com.popupwindowlibrary.interfaces.OnPopWindowDismissListener;

import java.util.List;

public class PopupUtils extends PopupWindow implements AdapterView.OnItemClickListener {

    private View conentView;
    private Activity activity;
    private List<PopWindowInfo> infoList;

    /** 数据 */
    private ListView mListView;

    /**
     * 适配器
     */
    private CouponsPopWindowAdapter myAdapter;

    /**
     * 是否显示动画效果，默认不显示
     */
    private boolean isAnimation = false;

    /**
     * 选择图标
     */
    private int ids = -1;

    /**
     * 适配器
     */
    private BaseAdapter adapter;

    /**
     * 动画样式
     */
    private int myAnimationStyle = -1;


    public static PopupUtils getInstance(){
        return new PopupUtils();
    }

    public Activity getActivity() {
        return activity;
    }

    public List<PopWindowInfo> getInfoList() {
        return infoList;
    }

    public int getMyAnimationStyle() {
        return myAnimationStyle;
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public int getIds() {
        return ids;
    }
    
    public boolean isAnimation() {
        return isAnimation;
    }
    
    public PopupUtils setActivity(Activity activity) {
        this.activity = activity;
        if (getActivity() == null){
            new NullPointerException("上下文不能为空");
        }
        return this;
    }

    public PopupUtils setInfoList(List<PopWindowInfo> infoList) {
        this.infoList = infoList;
        if (getInfoList() == null){
            new NullPointerException("数据不能为空");
        }
        return this;
    }

    public PopupUtils setMyAnimationStyle(int myAnimationStyle) {
        this.myAnimationStyle = myAnimationStyle;
        return this;
    }

    public PopupUtils setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public PopupUtils setIds(int ids) {
        this.ids = ids;
        return this;
    }

    public PopupUtils setAnimation(boolean animation) {
        isAnimation = animation;
        return this;
    }


    public PopupUtils(){
    }

    public PopupUtils init(){
        init(getAdapter());
        return this;
    }

    private void init(BaseAdapter adapter){
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window, null);

        initView(conentView, adapter);

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);

        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        if (isAnimation){
            if (getMyAnimationStyle() != -1){
                this.setAnimationStyle(getMyAnimationStyle());
            } else {
                this.setAnimationStyle(R.style.top_down);
            }
        }
    }

    /**
     * 初始化
     *
     * @param view
     */
    public void initView(final View view, BaseAdapter adapter) {
        mListView = (ListView) view.findViewById(R.id.listView);

        if(adapter == null){
            if (myAdapter == null) {
                myAdapter = new CouponsPopWindowAdapter(activity, getInfoList(), getIds());
            }
            mListView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
        } else {
            mListView.setAdapter(adapter);
        }

        mListView.setOnItemClickListener(this);

        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                ListAdapter getAdapter = mListView.getAdapter();
                View listItem = getAdapter.getView(1, null, mListView);
                listItem.measure(0, 0);
                int measuredHeight = listItem.getMeasuredHeight();

                measuredHeight = measuredHeight * getInfoList().size();// listview内容的总高度

                int y = (int) event.getY();// 手指按下的高度
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > measuredHeight) {
                        dismiss();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            this.showAsDropDown(parent);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        backgroundAlpha(1f);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent, int gravity) {
        if (!this.isShowing()) {
            int[] location = new int[2];
            parent.getLocationOnScreen(location);
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            //Gravity.BOTTOM
            showAtLocation(parent, gravity, 0,0);
            backgroundAlpha(0.3f);
        } else {
            dismiss();
        }
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.dismiss();
        if (listener != null) {
            listener.onPopWindowDismiss(this);
            listener.actionSheetItemClick(position);
        }
    }

    /** 监听者 */
    private ActionSheetItemClick listener;

    public ActionSheetItemClick getListener() {
        return listener;
    }

    public void setListener(ActionSheetItemClick listener) {
        this.listener = listener;
    }

    /**
     * 菜单点击
     *
     * @author apple
     *
     */
    public interface ActionSheetItemClick extends OnPopWindowDismissListener {
        /**
         * @param position
         */
        public void actionSheetItemClick(int position);
    }

}
