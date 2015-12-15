package com.qlfsoft.wordman.widget;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

public class PullScrollView extends ScrollView {
    /**
     * ����ϵ��,ԽС������Խ��.
     */
    private static final float SCROLL_RATIO = 0.33f;

    /**
     * ��������ת�ľ���.
     */
    private static final int TURN_DISTANCE = 50;

    /**
     * ͷ��ͼƬ.
     */
    private ImageView mHeadImage;

    /**
     * ͷ��ͼƬ��ʾ�߶�.
     */
    private int mHeadImageH;

    /**
     * ����View.
     */
    private View mChildView;

    /**
     * ScrollView�ĺ���View����.
     */
    private Rect mRect = new Rect();

    /**
     * �״ε����Y����.
     */
    private float mTouchDownY;

    /**
     * �Ƿ�ر�ScrollView�Ļ���.
     */
    private boolean mEnableTouch = false;

    /**
     * �Ƿ�ʼ�ƶ�.
     */
    private boolean isMoving = false;

    /**
     * ͷ��ͼƬ��ʼ�����͵ײ�.
     */
    private int mInitTop, mInitBottom;

    /**
     * ͷ��ͼƬ�϶�ʱ�����͵ײ�.
     */
    private int mCurrentTop, mCurrentBottom;

    /**
     * ״̬�仯ʱ�ļ�����.
     */
    private OnTurnListener mOnTurnListener;

    /**
     * ״̬���ϲ����²���Ĭ��.
     */
    private enum State {
        UP, DOWN, NORMAL
    }

    public static int mWidth;
    private boolean aBoolean = false;

    /**
     * ״̬.
     */
    private State state = State.NORMAL;

    public PullScrollView(Context context) {
        super(context);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * ��ʼ��
     *
     * @param imageView ͷ��ͼƬ
     */
    public void init(ImageView imageView) {
        mHeadImage = imageView;
        mHeadImageH = 94;
    }

    /**
     * ����״̬�ı�ʱ�ļ�����
     *
     * @param turnListener
     */
    public void setOnTurnListener(OnTurnListener turnListener) {
        mOnTurnListener = turnListener;
    }

    /**
     * ���� XML ������ͼ�������.�ú�����������ͼ�������ã�����������ͼ�����֮��.
     * ��ʹ���า���� onFinishInflate ������ҲӦ�õ��ø���ķ�����ʹ�÷�������ִ��.
     */
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mChildView = getChildAt(0);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        // ������������ʱ����״̬����Ϊ�����������������϶��������϶������˺��״δ�������Ӧ������
        if (getScrollY() == 0) {
            state = State.NORMAL;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mChildView != null) {
            doTouchEvent(ev);
        }

        // ��ֹ�ؼ�����Ļ���.
        if (mEnableTouch) {
            return true;
        } else {
            return super.onTouchEvent(ev);
        }
    }

    /**
     * �����¼����� ���ܳ���һ��λ�ã���׼�ϻ�
     *
     * @param event
     */
    private void doTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //Log.e("", "" + event.getY());
                mTouchDownY = event.getY();
                mCurrentTop = mInitTop = mHeadImage.getTop();
                mCurrentBottom = mInitBottom = mHeadImage.getBottom();
                if (event.getY() < mWidth) {
                    aBoolean = true;
                } else {
                    aBoolean = false;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float deltaY = event.getY() - mTouchDownY;
                //Log.e("", "" + deltaY);
                if (aBoolean) {
                    doActionMove(deltaY);
                } else {
                    doActionMove(0);
                }
                break;

            case MotionEvent.ACTION_UP:
                // �ع�����
                if (isNeedAnimation()) {
                    rollBackAnimation();
                }

                if (getScrollY() == 0) {
                    state = State.NORMAL;
                }

                isMoving = false;
                mEnableTouch = false;
                aBoolean = false;
                break;

            default:
                break;
        }
    }

    /**
     * ִ���ƶ�����
     *
     * @param deltaY
     */
    private void doActionMove(float deltaY) {
        // �����״�Touch����Ҫ�жϷ�λ��UP OR DOWN
        if (deltaY < 0 && state == State.NORMAL) {
            state = State.UP;
        } else if (deltaY > 0 && state == State.NORMAL) {
            state = State.DOWN;
        }

        if (state == State.UP) {
            deltaY = deltaY < 0 ? deltaY : 0;

            isMoving = false;
            mEnableTouch = false;

        } else if (state == State.DOWN) {
            if (getScrollY() <= deltaY) {
                mEnableTouch = true;
                isMoving = true;
            }
            deltaY = deltaY < 0 ? 0 : deltaY;
        }

        if (isMoving) {
            // ��ʼ��ͷ������
            if (mRect.isEmpty()) {
                // ���������Ĳ���λ��
                mRect.set(
                        mChildView.getLeft(), mChildView.getTop(), mChildView.getRight(),
                        mChildView.getBottom()
                );
            }

            // �ƶ�����ͼ(�����ƶ��ľ���*����ϵ��*0.5)
            float bgMoveH = deltaY * 0.5f * SCROLL_RATIO;
            mCurrentTop = (int) (mInitTop + bgMoveH);
            mCurrentBottom = (int) (mInitBottom + bgMoveH);
            mHeadImage.layout(
                    mHeadImage.getLeft(), mCurrentTop, mHeadImage.getRight(),
                    mCurrentBottom
            );

            // �ƶ�����(�����ƶ��ľ���*����ϵ��)
            float childMoveH = deltaY * SCROLL_RATIO;

            // �����ƶ��ľ��룬���ⳬ��ͼƬ�ĵױ�Ե
            int top = mCurrentBottom - mHeadImageH;
            if (mRect.top + childMoveH > top) {
                childMoveH -= mRect.top + childMoveH - top;
            }

            mChildView.layout(
                    mRect.left, (int) (mRect.top + childMoveH),
                    mRect.right, (int) (mRect.bottom + childMoveH)
            );
        }
    }

    /**
     * �ع�����
     */
    private void rollBackAnimation() {
        TranslateAnimation image_Anim = new TranslateAnimation(
                0, 0,
                Math.abs(mInitTop - mCurrentTop), 0
        );
        image_Anim.setDuration(200);
        mHeadImage.startAnimation(image_Anim);

        mHeadImage.layout(mHeadImage.getLeft(), mInitTop, mHeadImage.getRight(), mInitBottom);

        // �����ƶ�����
        TranslateAnimation inner_Anim = new TranslateAnimation(0, 0, mChildView.getTop(), mRect.top);
        inner_Anim.setDuration(200);
        mChildView.startAnimation(inner_Anim);
        mChildView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);

        mRect.setEmpty();

        // �ص�������
        if (mCurrentTop > mInitTop + TURN_DISTANCE && mOnTurnListener != null) {
            mOnTurnListener.onTurn();
        }
    }

    /**
     * �Ƿ���Ҫ��������
     */
    private boolean isNeedAnimation() {
        return !mRect.isEmpty() && isMoving;
    }

    /**
     * ִ�з�ת
     *
     * @author MarkMjw
     */
    public interface OnTurnListener {
        /**
         * ��ת�ص�����
         */
        public void onTurn();
    }
}
