package com.lib.views.recyclerview;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.flyco.tablayout.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


/**
 * RecycleyAdapter基类
 * @param <T>
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean mNextLoadEnable = false;
    private boolean mLoadingMoreEnable = false;
    private boolean mFirstOnlyEnable = true;
    private boolean mOpenAnimationEnable = false;
    private boolean mEmptyEnable;
    private boolean mHeadAndEmptyEnable;
    private boolean mFootAndEmptyEnable;
    private Interpolator mInterpolator = new LinearInterpolator();
    private int mDuration = 300;
    private int mLastPosition = -1;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    private View mHeaderView;
    private View mFooterView;
    private int pageSize = -1;
    private View mContentView;
    /**
     * View to show if there are no items to show.
     */
    private View mEmptyView;

    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();
    protected WeakReference<Context> mContext;
    protected int mLayoutResId;
    protected LayoutInflater mLayoutInflater;
    protected List<T> mData;
    protected static final int HEADER_VIEW = 0x00000111;
    protected static final int LOADING_VIEW = 0x00000222;
    protected static final int FOOTER_VIEW = 0x00000333;
    protected static final int EMPTY_VIEW = 0x00000555;
    private View mLoadingView;

    private static final int NO_TOGGLE_VIEW = 0;
    private int mToggleViewId = NO_TOGGLE_VIEW;
    private ItemTouchHelper mItemTouchHelper;
    private boolean itemDragEnabled = false;
    private boolean mDragOnLongPress = true;

    private View.OnTouchListener mOnToggleViewTouchListener;
    private View.OnLongClickListener mOnToggleViewLongClickListener;

    private static final String ERROR_NOT_SAME_ITEMTOUCHHELPER = "Item drag and item swipe should pass the same ItemTouchHelper";
    private int height,width;

    @Deprecated
    public void setOnLoadMoreListener(int pageSize, RequestLoadMoreListener requestLoadMoreListener) {

        setOnLoadMoreListener(requestLoadMoreListener);
    }

    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
    }

    /**
     * Sets the duration of the animation.
     *
     * @param duration The length of the animation, in milliseconds.
     */
    public void setDuration(int duration) {
        mDuration = duration;
    }

    /**
     * when adapter's data size than pageSize and enable is true,the loading more function is enable,or disable
     *
     * @param pageSize
     * @param enable
     */
    public void openLoadMore(int pageSize, boolean enable) {
        this.pageSize = pageSize;
        mNextLoadEnable = enable;

    }

    /**
     * call the method before you should call setPageSize() method to setting up the enablePagerSize value,whether it will  invalid
     * enable the loading more data function if enable's value is true,or disable
     *
     * @param enable
     */
    public void openLoadMore(boolean enable) {
        mNextLoadEnable = enable;

    }

    /**
     * setting up the size to decide the loading more data funcation whether enable
     * enable if the data size than pageSize,or diable
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * return the value of pageSize
     *
     * @return
     */
    public int getPageSize() {
        return this.pageSize;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param onRecyclerViewItemClickListener The callback that will be invoked.
     */
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * AdapterView has been clicked.
     */
    public interface OnRecyclerViewItemClickListener {
        /**
         * Callback method to be invoked when an item in this AdapterView has
         * been clicked.
         *
         * @param view     The view within the AdapterView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(View view, int position);
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked and held
     *
     * @param onRecyclerViewItemLongClickListener The callback that will run
     */
    public void setOnRecyclerViewItemLongClickListener(OnRecyclerViewItemLongClickListener onRecyclerViewItemLongClickListener) {
        this.onRecyclerViewItemLongClickListener = onRecyclerViewItemLongClickListener;
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * view has been clicked and held
     */
    public interface OnRecyclerViewItemLongClickListener {
        /**
         * callback method to be invoked when an item in this view has been
         * click and held
         *
         * @param view     The view whihin the AbsListView that was clicked
         * @param position The position of the view int the adapter
         * @return true if the callback consumed the long click ,false otherwise
         */
        boolean onItemLongClick(View view, int position);
    }

    private OnRecyclerViewItemChildClickListener mChildClickListener;

    /**
     * Register a callback to be invoked when childView in this AdapterView has
     * been clicked and held
     * {@link OnRecyclerViewItemChildClickListener}
     *
     * @param childClickListener The callback that will run
     */
    public void setOnRecyclerViewItemChildClickListener(OnRecyclerViewItemChildClickListener childClickListener) {
        this.mChildClickListener = childClickListener;
    }

    public interface OnRecyclerViewItemChildClickListener {
        void onItemChildClick(BaseQuickAdapter adapter, View view, int position);
    }

    public class OnItemChildClickListener implements View.OnClickListener {
        public RecyclerView.ViewHolder mViewHolder;

        @Override
        public void onClick(View v) {
            if (mChildClickListener != null)
                mChildClickListener.onItemChildClick(BaseQuickAdapter.this, v, mViewHolder.getLayoutPosition() - getHeaderViewsCount());
        }
    }


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(List<T> data, Context cxt) {
        this.mContext = new WeakReference<>(cxt);
        this.mData = data == null ? new ArrayList<T>() : data;
        int layoutResId = setItemLayout();
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }
    public BaseQuickAdapter(Context cxt) {
        this(null, cxt);
    }

    public BaseQuickAdapter(View contentView, List<T> data , Context cxt) {
        this(data, cxt);
        mContentView = contentView;
    }

    /**
     * remove the item associated with the specified position of adapter
     *
     * @param position
     */
    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position + getHeaderViewsCount());

    }

    /**
     * insert  a item associated with the specified position of adapter
     *
     * @param position
     * @param item
     */
    public void add(int position, T item) {
        mData.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * insert  a item associated of adapter
     *
     * @param item
     */
    public void add(T item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    /**
     * setting up a new instance to data;
     *
     * @param data
     */
    public void setNewData(List<T> data) {
        this.mData = data;
        if (mRequestLoadMoreListener != null) {
            mNextLoadEnable = true;
            mFooterView = null;
        }
        mLastPosition = -1;
        notifyDataSetChanged();
    }

    /**
     * additional data;
     *
     * @param data
     */
    public void addData(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * set a loadingView
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
    }

    /**
     * Get the data of list
     *
     * @return
     */
    public List getData() {
        return mData;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * if setHeadView will be return 1 if not will be return 0
     *
     * @return
     */
    public int getHeaderViewsCount() {
        return mHeaderView == null ? 0 : 1;
    }

    /**
     * if mFooterView will be return 1 or not will be return 0
     *
     * @return
     */
    public int getFooterViewsCount() {
        return mFooterView == null ? 0 : 1;
    }

    /**
     * if mEmptyView will be return 1 or not will be return 0
     *
     * @return
     */
    public int getmEmptyViewCount() {
        return mEmptyView == null ? 0 : 1;
    }

    /**
     * returns the number of item that will be created
     *
     * @return
     */
    @Override
    public int getItemCount() {
        int i = isLoadMore() ? 1 : 0;
        int count = mData.size() + i + getHeaderViewsCount() + getFooterViewsCount();
        if (mData.size() == 0 && mEmptyView != null) {
            /**
             *  setEmptyView(false) and add emptyView
             */
            if (count == 0 && (!mHeadAndEmptyEnable || !mFootAndEmptyEnable)) {
                count += getmEmptyViewCount();
                /**
                 * {@link #setEmptyView(true, true, View)}
                 */
            } else if (mHeadAndEmptyEnable || mFootAndEmptyEnable) {
                count += getmEmptyViewCount();
            }

            if ((mHeadAndEmptyEnable && getHeaderViewsCount() == 1 && count == 1) || count == 0) {
                mEmptyEnable = true;
                count += getmEmptyViewCount();
            }

        }
        return count;
    }

    /**
     * Get the type of View that will be created by {@link #getItemView(int, ViewGroup)} for the specified item.
     *
     * @param position The position of the item within the adapter's data set whose view type we
     *                 want.
     * @return An integer representing the type of View. Two views should share the same type if one
     * can be converted to the other in {@link #getItemView(int, ViewGroup)}. Note: Integers must be in the
     * range 0 to {@link #getItemCount()} - 1.
     */
    @Override
    public int getItemViewType(int position) {
        /**
         * if set headView and positon =0
         */
        if (mHeaderView != null && position == 0) {
            return HEADER_VIEW;
        }
        /**
         * if user has no data and add emptyView and position <2{(headview +emptyView)}
         */
        if (mData.size() == 0 && mEmptyEnable && mEmptyView != null && position <= 2) {
            /**
             * if set {@link #setEmptyView(boolean, boolean, View)}  position = 1
             */
            if ((mHeadAndEmptyEnable || mFootAndEmptyEnable) && position == 1) {
                /**
                 * if user want to show headview and footview and emptyView but not add headview
                 */
                if (mHeaderView == null && mEmptyView != null && mFooterView != null) {
                    return FOOTER_VIEW;
                    /**
                     * add headview
                     */
                } else if (mHeaderView != null && mEmptyView != null) {
                    return EMPTY_VIEW;
                }
            } else if (position == 0) {
                /**
                 * has no emptyView just add emptyview
                 */
                if (mHeaderView == null) {
                    return EMPTY_VIEW;
                } else if (mFooterView != null)

                    return EMPTY_VIEW;


            } else if (position == 2 && (mFootAndEmptyEnable || mHeadAndEmptyEnable) && mHeaderView != null && mEmptyView != null) {
                return FOOTER_VIEW;

            } /**
             * user forget to set {@link #setEmptyView(boolean, boolean, View)}  but add footview and headview and emptyview
             */
            else if ((!mFootAndEmptyEnable || !mHeadAndEmptyEnable) && position == 1 && mFooterView != null) {
                return FOOTER_VIEW;
            }
        } else if (mData.size() == 0 && mEmptyView != null && getItemCount() == (mHeadAndEmptyEnable ? 2 : 1) && mEmptyEnable) {
            return EMPTY_VIEW;
        } else if (position == mData.size() + getHeaderViewsCount()) {
            if (mNextLoadEnable)
                return LOADING_VIEW;
            else
                return FOOTER_VIEW;
        }
        return getDefItemViewType(position - getHeaderViewsCount());
    }

    protected int getDefItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = null;
        this.mLayoutInflater = LayoutInflater.from(mContext.get());
        switch (viewType) {
            case LOADING_VIEW:
                baseViewHolder = getLoadingView(parent);
                break;
            case HEADER_VIEW:
                baseViewHolder = new BaseViewHolder(mHeaderView);
                break;
            case EMPTY_VIEW:
                baseViewHolder = new BaseViewHolder(mEmptyView);
                break;
            case FOOTER_VIEW:
                baseViewHolder = new BaseViewHolder(mFooterView);
                break;
            default:
                //(ViewGroup) ((Activity)mContext.get()).findViewById(android.R.id.content)
                baseViewHolder = onCreateDefViewHolder(parent, viewType);
                height = parent.getHeight();
                width = parent.getWidth();
                initItemClickListener(baseViewHolder);
        }
        return baseViewHolder;

    }


    private BaseViewHolder getLoadingView(ViewGroup parent) {
        if (mLoadingView == null) {
            return createBaseViewHolder(parent, R.layout.view_pull_refresh);
        }
        return new BaseViewHolder(mLoadingView);
    }

    /**
     * Called when a view created by this adapter has been attached to a window.
     * simple to solve item will layout using all
     * {@link #setFullSpan(RecyclerView.ViewHolder)}
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int type = holder.getItemViewType();
        if (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) {
            setFullSpan(holder);
        }
    }

    /**
     * When set to true, the item will layout using all span area. That means, if orientation
     * is vertical, the view will have full width; if orientation is horizontal, the view will
     * have full height.
     * if the hold view use StaggeredGridLayoutManager they should using all span area
     *
     * @param holder True if this item should traverse all spans.
     */
    protected void setFullSpan(RecyclerView.ViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    return (type == EMPTY_VIEW || type == HEADER_VIEW || type == FOOTER_VIEW || type == LOADING_VIEW) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * To bind different types of holder and solve different the bind events
     *
     * @param holder
     * @param positions
     * @see #getDefItemViewType(int)
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int positions) {
        int viewType = holder.getItemViewType();

        switch (viewType) {
            case 0:
                convert((BaseViewHolder) holder, mData.get(holder.getLayoutPosition() - getHeaderViewsCount()),positions);
                break;
            case LOADING_VIEW:
                addLoadMore(holder);
                break;
            case HEADER_VIEW:
                break;
            case EMPTY_VIEW:
                break;
            case FOOTER_VIEW:
                break;
            default:
                convert((BaseViewHolder) holder, mData.get(holder.getLayoutPosition() - getHeaderViewsCount()),positions);
                onBindDefViewHolder((BaseViewHolder) holder, mData.get(holder.getLayoutPosition() - getHeaderViewsCount()));
                break;
        }

        if (mItemTouchHelper != null && itemDragEnabled && viewType != LOADING_VIEW && viewType != HEADER_VIEW
                && viewType != EMPTY_VIEW && viewType != FOOTER_VIEW) {
            if (mToggleViewId != NO_TOGGLE_VIEW) {
                View toggleView = ((BaseViewHolder) holder).getView(mToggleViewId);
                if (toggleView != null) {
                    toggleView.setTag(holder);
                    if (mDragOnLongPress) {
                        toggleView.setOnLongClickListener(mOnToggleViewLongClickListener);
                    } else {
                        toggleView.setOnTouchListener(mOnToggleViewTouchListener);
                    }
                }
            } else {
                holder.itemView.setTag(holder);
                holder.itemView.setOnLongClickListener(mOnToggleViewLongClickListener);
            }
        }
    }

    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (mContentView == null) {
            View view = getItemView(layoutResId, parent);
            return new BaseViewHolder(view);
        }
        return new BaseViewHolder(mContentView);
    }

    /**
     * easy to show a simple headView
     *
     * @param header
     */
    public void addHeaderView(View header) {
        this.mHeaderView = header;
        this.notifyDataSetChanged();
    }

    /**
     * easy to show a simple footerView
     *
     * @param footer
     */
    public void addFooterView(View footer) {
        mNextLoadEnable = false;
        this.mFooterView = footer;
        this.notifyDataSetChanged();
    }

    /**
     * Sets the view to show if the adapter is empty
     */
    public void setEmptyView(View emptyView) {
        setEmptyView(false, false, emptyView);
    }

    /**
     * @param isHeadAndEmpty false will not show headView if the data is empty true will show emptyView and headView
     * @param emptyView
     */
    public void setEmptyView(boolean isHeadAndEmpty, View emptyView) {
        setEmptyView(isHeadAndEmpty, false, emptyView);
    }

    /**
     * set emptyView show if adapter is empty and want to show headview and footview
     *
     * @param isHeadAndEmpty
     * @param isFootAndEmpty
     * @param emptyView
     */
    public void setEmptyView(boolean isHeadAndEmpty, boolean isFootAndEmpty, View emptyView) {
        mHeadAndEmptyEnable = isHeadAndEmpty;
        mFootAndEmptyEnable = isFootAndEmpty;
        mEmptyView = emptyView;
        mEmptyEnable = true;
    }

    /**
     * When the current adapter is empty, the BaseQuickAdapter can display a special view
     * called the empty view. The empty view is used to provide feedback to the user
     * that no data is available in this AdapterView.
     *
     * @return The view to show if the adapter is empty.
     */
    public View getEmptyView() {
        return mEmptyView;
    }

    /**
     * see more {@link  public void notifyDataChangedAfterLoadMore(boolean isNextLoad)}
     *
     * @param isNextLoad
     */
    @Deprecated
    public void isNextLoad(boolean isNextLoad) {
        mNextLoadEnable = isNextLoad;
        mLoadingMoreEnable = false;
        notifyDataSetChanged();

    }

    /**
     * @param isNextLoad true
     *                   if true when loading more data can show loadingView
     */
    public void notifyDataChangedAfterLoadMore(boolean isNextLoad) {
        mNextLoadEnable = isNextLoad;
        mLoadingMoreEnable = false;
        notifyDataSetChanged();

    }

    /**
     * add more data
     *
     * @param data
     * @param isNextLoad
     */
    public void notifyDataChangedAfterLoadMore(List<T> data, boolean isNextLoad) {
        mData.addAll(data);
        notifyDataChangedAfterLoadMore(isNextLoad);

    }


    private void addLoadMore(RecyclerView.ViewHolder holder) {
        if (isLoadMore() && !mLoadingMoreEnable) {
            mLoadingMoreEnable = true;
            mRequestLoadMoreListener.onLoadMoreRequested();
        }
    }

    /**
     * init the baseViewHolder to register onRecyclerViewItemClickListener and onRecyclerViewItemLongClickListener
     *
     * @param baseViewHolder
     */
    private void initItemClickListener(final BaseViewHolder baseViewHolder) {
        if (onRecyclerViewItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClick(v, baseViewHolder.getLayoutPosition() - getHeaderViewsCount());
                }
            });
        }
        if (onRecyclerViewItemLongClickListener != null) {
            baseViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onRecyclerViewItemLongClickListener.onItemLongClick(v, baseViewHolder.getLayoutPosition() - getHeaderViewsCount());
                }
            });
        }
    }


    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolator);
    }

    /**
     * Determine whether it is loaded more
     *
     * @return
     */
    private boolean isLoadMore() {
        return mNextLoadEnable && pageSize != -1 && mRequestLoadMoreListener != null && mData.size() >= pageSize;
    }

    /**
     * @param layoutResId ID for an XML layout resource to load
     * @param parent      Optional view to be the parent of the generated hierarchy or else simply an object that
     *                    provides a set of LayoutParams values for root of the returned
     *                    hierarchy
     * @return view will be return
     */
    protected View getItemView(int layoutResId, ViewGroup parent) {
        return mLayoutInflater.inflate(layoutResId, parent, false);
    }

    /**
     * @see #(BaseViewHolder, Object ) ()
     * @deprecated This method is deprecated
     * {@link #(BaseViewHolder, Object )} depending on your use case.
     */
    @Deprecated
    protected void onBindDefViewHolder(BaseViewHolder holder, T item) {
    }

    public interface RequestLoadMoreListener {

        void onLoadMoreRequested();
    }

    /**
     * {@link #( RecyclerView.ViewHolder)}
     *
     * @param firstOnly true just show anim when first loading false show anim when load the data every time
     */
    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    protected abstract int setItemLayout();

    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(BaseViewHolder helper, T item, int position);



    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Set the toggle view's id which will trigger drag event.
     * If the toggle view id is not set, drag event will be triggered when the item is long pressed.
     *
     * @param toggleViewId the toggle view's id
     */
    public void setToggleViewId(int toggleViewId) {
        mToggleViewId = toggleViewId;
    }

    /**
     * Set the drag event should be trigger on long press.
     * Work when the toggleViewId has been set.
     *
     * @param longPress by default_0 is true.
     */
    public void setToggleDragOnLongPress(boolean longPress) {
        mDragOnLongPress = longPress;
        if (mDragOnLongPress) {
            mOnToggleViewTouchListener = null;
            mOnToggleViewLongClickListener = new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mItemTouchHelper != null && itemDragEnabled) {
                        mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag());
                    }
                    return true;
                }
            };
        } else {
            mOnToggleViewTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN
                            && !mDragOnLongPress) {
                        if (mItemTouchHelper != null && itemDragEnabled) {
                            mItemTouchHelper.startDrag((RecyclerView.ViewHolder) v.getTag());
                        }
                        return true;
                    } else {
                        return false;
                    }
                }
            };
            mOnToggleViewLongClickListener = null;
        }
    }

    /**
     * Enable drag items.
     * Use itemView as the toggleView when long pressed.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper) {
        enableDragItem(itemTouchHelper, NO_TOGGLE_VIEW, true);
    }

    /**
     * Enable drag items. Use the specified view as toggle.
     *
     * @param itemTouchHelper {@link ItemTouchHelper}
     * @param toggleViewId    The toggle view's id.
     * @param dragOnLongPress If true the drag event will be trigger on long press, otherwise on touch down.
     */
    public void enableDragItem(@NonNull ItemTouchHelper itemTouchHelper, int toggleViewId, boolean dragOnLongPress) {
        itemDragEnabled = true;
        mItemTouchHelper = itemTouchHelper;
        setToggleViewId(toggleViewId);
        setToggleDragOnLongPress(dragOnLongPress);
    }


    public int getViewHolderPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition() - getHeaderViewsCount();
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    protected void setTextValid(TextView tv, Object obj) {
        if (obj != null) {
            if (obj instanceof String)
                tv.setText((String) obj);
            else
                tv.setText(obj.toString());
        } else {
            tv.setText("");
        }
    }
}
