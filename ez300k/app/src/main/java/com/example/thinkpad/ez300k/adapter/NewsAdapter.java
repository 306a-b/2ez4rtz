package com.example.thinkpad.ez300k.adapter;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.thinkpad.ez300k.NewsInfo;
import com.example.thinkpad.ez300k.R;
import com.example.thinkpad.ez300k.Song;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String LOG_TAG = "tag";
    public static final String LOG_ERROR = "CARDADAPTER ERROR: ";
    private Context mContext;
    private ArrayList<NewsInfo> mData;
    private ArrayList<Integer> mViewDataType;

    public class ExpandListClick implements  View.OnClickListener {
        private ListView songView;
        private ArrayList<Song> songsList;
        private Context mContext;
        private ImageButton mImageButton;
        private ValueAnimator mAnimator;
        private int height;
        private SongsListAdapter adapter;

        private boolean isExpanded;

        public ExpandListClick(Context context, ListView songView, ArrayList<Song> songsList, ImageButton imageButton){
            this.songView = songView;
            this.songsList = songsList;
            this.mContext = context;
            this.mImageButton = imageButton;
            isExpanded = false;
            this.height = -1;
        }

        @Override
        public void onClick(View v) {
            if (height==-1) {
                adapter = new SongsListAdapter(mContext, this.songsList);
                this.songView.setAdapter(adapter);
                this.height = NewsAdapter.getListViewHeightByAdapter(songView, adapter);
            }

            if (!isExpanded){
                isExpanded = true;
                expand();
            } else {
                isExpanded = false;
                collapse();
            }

        }

        private void expand() {
            songView.setVisibility(View.VISIBLE);
            final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            songView.measure(widthSpec, heightSpec);
            mAnimator = slideAnimator(0, this.height);
            mAnimator.start();
        }

        private void collapse() {
            ValueAnimator mAnimator = slideAnimator(this.height, 0);

            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationEnd(Animator animator) {
                    songView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationStart(Animator animator) {}

                @Override
                public void onAnimationCancel(Animator animator) {}

                @Override
                public void onAnimationRepeat(Animator animator) {}
            });
            mAnimator.start();
        }

        private ValueAnimator slideAnimator(int start, int end) {
            ValueAnimator animator = ValueAnimator.ofInt(start, end);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //Update Height
                    int value = (Integer) valueAnimator.getAnimatedValue();

                    ViewGroup.LayoutParams layoutParams = songView.getLayoutParams();
                    layoutParams.height = value;
                    songView.setLayoutParams(layoutParams);
                }
            });
            return animator;
        }
    }

    public static class ViewHolderCardAlbum extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView albumImage;
        public TextView albumName;
        public TextView singerName;

        public Button playButton;
        public Button addPlaylistButton;
        public ImageButton expandButton;
        public ListView songView;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.playButton:
                    Log.i(NewsAdapter.LOG_TAG, "press the button1");
                    break;
                case R.id.addPlaylistButton:
                    Log.i(NewsAdapter.LOG_TAG, "press the button2");
                    break;
                case R.id.expandButton:
                    Log.i(NewsAdapter.LOG_TAG, "press the buttone");

                    break;
            }
        }

        public ViewHolderCardAlbum(View v) {
            super(v);
            this.albumImage = (ImageView) v.findViewById(R.id.albumImage);
            this.albumName = (TextView) v.findViewById(R.id.albumName);
            this.singerName = (TextView) v.findViewById(R.id.singerName);
            this.playButton = (Button) v.findViewById(R.id.playButton);
            this.addPlaylistButton = (Button) v.findViewById(R.id.addPlaylistButton);
            this.expandButton = (ImageButton) v.findViewById(R.id.expandButton);
            this.songView = (ListView) v.findViewById(R.id.songsListView);
        }
    }
    public static class ViewHolderCardAlbumNoImage extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView singerNameNoImage;
        public ImageView singerImage;

        public Button playButton;
        public Button addPlaylistButton;
        public ImageButton expandButton;
        public ListView songView;

        @Override
        public void onClick(View v) {

        }

        public ViewHolderCardAlbumNoImage(View v) {
            super(v);
            this.singerNameNoImage = (TextView) v.findViewById(R.id.singerNameNoImage);
            this.singerImage = (ImageView) v.findViewById(R.id.singerImage);
            this.playButton = (Button) v.findViewById(R.id.playButton);
            this.addPlaylistButton = (Button) v.findViewById(R.id.addPlaylistButton);
            this.expandButton = (ImageButton) v.findViewById(R.id.expandButton);
            this.songView = (ListView) v.findViewById(R.id.songsListView);
        }
    }

    public NewsAdapter(Context context, ArrayList<NewsInfo> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == NewsInfo.CARD_TYPE.ALBUM_IMAGE.ordinal()) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album_image, parent, false);
            vh = new ViewHolderCardAlbum(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_album_no_image, parent, false);
            vh = new ViewHolderCardAlbumNoImage(v);
        }

        return vh;
    }

    @Override
    public int getItemViewType(int pos) {
        return mData.get(pos).getType().ordinal();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        final NewsInfo newsInfo = mData.get(pos);

        int viewType = viewHolder.getItemViewType();

        if (viewType == NewsInfo.CARD_TYPE.ALBUM_IMAGE.ordinal()) {
            ViewHolderCardAlbum holder = (ViewHolderCardAlbum) viewHolder;
            holder.albumName.setText(newsInfo.albumName);
            holder.singerName.setText(newsInfo.singerName);
            Picasso.with(mContext)
                    .load(newsInfo.albumImageURL)
                    .error(R.drawable.error_image)
                    .placeholder(R.drawable.error_image)
                    .into(holder.albumImage);

            holder.addPlaylistButton.setOnClickListener(holder);
            holder.playButton.setOnClickListener(holder);
            holder.expandButton.setOnClickListener(
                    new ExpandListClick(mContext, holder.songView, newsInfo.songsList, holder.expandButton));

        } else {
            ViewHolderCardAlbumNoImage holder = (ViewHolderCardAlbumNoImage) viewHolder;
            holder.singerNameNoImage.setText(newsInfo.singerName);
            Picasso.with(mContext)
                    .load(newsInfo.albumImageURL)
                    .error(R.drawable.error_image)
                    .placeholder(R.drawable.error_image)
                    .into(holder.singerImage);

            holder.addPlaylistButton.setOnClickListener(holder);
            holder.playButton.setOnClickListener(holder);
            holder.expandButton.setOnClickListener(holder);
            holder.expandButton.setOnClickListener(
                    new ExpandListClick(mContext, holder.songView, newsInfo.songsList, holder.expandButton));

        }
    }

    public static void scaleView(View v, float startScaleY, float endScaleY) {
        Animation anim = new ScaleAnimation(
                1f, 1f, // Start and end values for the X axis scaling
                startScaleY, endScaleY, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        v.startAnimation(anim);
    }


    public static void justifyListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter adapter = listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

    public static int getListViewHeightByAdapter(ListView listView, SongsListAdapter adapter) {
        if (adapter == null) {
            return 0;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        return totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
    }

    public static void setListViewHeight(ListView listView, int height) {
        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = height;
        listView.setLayoutParams(par);
        listView.requestLayout();
    }

}
