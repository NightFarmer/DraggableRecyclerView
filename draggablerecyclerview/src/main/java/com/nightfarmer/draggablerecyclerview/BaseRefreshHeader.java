package com.nightfarmer.draggablerecyclerview;

/**
 * Created by zhangfan on 16-1-6.
 */
interface  BaseRefreshHeader {
    public void onMove(float delta, DraggableRecyclerView.LoadingListener mLoadingListener) ;
    public boolean releaseAction();
    public void refreshComplete();
    public final static int STATE_NORMAL = 0;
    public final static int STATE_RELEASE_TO_REFRESH = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_DONE = 3;
}
