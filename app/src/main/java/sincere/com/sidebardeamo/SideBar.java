package sincere.com.sidebardeamo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by zengqiang on 2016/8/30.
 * Description:字母滑动列表，可显示当前选中字母
 */
public class SideBar extends View {
    private String[] Letter;//绘制列表，英文字母
//    private ListView listView;
    private TextView dialogText;//弹出字体
    private int itemHeight; //item高度
    private int textSize;//字体大小
    private int selectPos = -1;//当前选中或者滑动到的位置
    private Paint paint = new Paint();  //画笔
//    private BaseSectionIndexerAdapter baseSectionIndexerAdapter = null;

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
    public void setDialogText(Context context,int id) {
        WindowManager windowManager= (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        TextView dialogText= (TextView) View.inflate(context,id,null);
        WindowManager.LayoutParams layoutParams=
                new WindowManager.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        PixelFormat.TRANSLUCENT);
        windowManager.addView(dialogText,layoutParams);
        Point metric=getScreenMetrics(context);
        setItemHeight((metric.y-dip2px(context,100))/30);
        this.dialogText = dialogText;
    }

    //改变ListView的选中位置
//    public void setListView(ListView listView) {
//        this.listView = listView;
//        if (listView.getAdapter() instanceof HeaderViewListAdapter) {
//            HeaderViewListAdapter listAdapter =
// (HeaderViewListAdapter) listView.getAdapter();
//            baseSectionIndexerAdapter =
// (BaseSectionIndexerAdapter) listAdapter.getWrappedAdapter();
//        } else
//            baseSectionIndexerAdapter =
// (BaseSectionIndexerAdapter) listView.getAdapter();
//    }

    public SideBar(Context context) {
        this(context, null);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        itemHeight = dip2px(context, 16);
        textSize = dip2px(context, 12);
        initPaint();
        Letter = new String[]
                {"*", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                        "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                        "W", "X", "Y", "Z"};
        //子线程刷新(重绘)
//        postInvalidate();
        //主线程刷新(重绘)
//        invalidate();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        paint.setColor(Color.GRAY);
        paint.setTextSize(textSize);
        paint.setTypeface(font);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int i = (int) event.getY();
        int idx  = i / itemHeight;//得到滑动的item个数
        //防止出现数组越界
        if (idx >= Letter.length)
            idx = Letter.length - 1;
        else if (idx < 0)
            idx = 0;
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            selectPos = idx;
            invalidate();  //重绘
            //显示弹出TextView
            dialogText.setVisibility(VISIBLE);
            dialogText.setTextSize(50);
            dialogText.setText(Letter[idx]);
            //改变ListView选中位置
//            if (baseSectionIndexerAdapter == null) {
//                if (listView.getAdapter() instanceof HeaderViewListAdapter)
// {//ListView有headerView
//                    HeaderViewListAdapter listAdapter =
// (HeaderViewListAdapter) listView.getAdapter();
//                    baseSectionIndexerAdapter =
// (BaseSectionIndexerAdapter) listAdapter.getWrappedAdapter();
//                } else {//没有headerView
//                    baseSectionIndexerAdapter =
// (BaseSectionIndexerAdapter) listView.getAdapter();
//                }
//            }
            //获得置顶位置
//            int position = baseSectionIndexerAdapter.
// getPositionForSection(Letter[idx].charAt(0));
//            if (position == -1)
//                return true;
//            listView.setSelection(position);
        } else {
            selectPos = -1;
            dialogText.setVisibility(GONE);
            invalidate();
        }
        return true;
    }

    /**
     * 绘制文字
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        float widthCenter = getMeasuredWidth() / 2;
        for (int i = 0; i < Letter.length; i++) {
            if (i == selectPos)
                //设置选中的字母时画笔颜色为蓝色
                paint.setColor(Color.BLUE);
            else
                paint.setColor(Color.GRAY);
            //画文字
            canvas.drawText(Letter[i], widthCenter, itemHeight * (i + 1), paint);
            //画bitmap
//            canvas.drawBitmap();
//            canvas.drawLine();
//            canvas.drawCircle();
//            canvas.drawArc();//画弧形
        }
        super.onDraw(canvas);
    }

    /**
     * dip转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度和高度，单位为px
     *
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);

    }
}
