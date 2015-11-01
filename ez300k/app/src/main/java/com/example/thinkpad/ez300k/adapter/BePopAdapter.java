package com.example.thinkpad.ez300k.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thinkpad.ez300k.Song;
import com.example.thinkpad.ez300k.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BePopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public enum VIEW_TYPE { IMAGE, NO_IMAGE }

    private ArrayList<Song> mData;
    private ArrayList<Integer> mViewDataType;
    private Context mContext;



    public class ViewHolderBePopSongNoImage extends RecyclerView.ViewHolder {
        public TextView songName;
        public TextView artistName;

        public ImageButton likeButton;
        public ImageButton addPlaylistButton;
        public ImageButton addToMeButton;

        public ViewHolderBePopSongNoImage(View itemView) {
            super(itemView);
            this.songName = (TextView) itemView.findViewById(R.id.songName);
            this.artistName = (TextView) itemView.findViewById(R.id.artistName);
            this.likeButton = (ImageButton) itemView.findViewById(R.id.likeButton);
            this.addPlaylistButton = (ImageButton) itemView.findViewById(R.id.addPlaylistButton);
            this.addToMeButton = (ImageButton) itemView.findViewById(R.id.addToMeButton);
        }

    }

    public class ViewHolderBePopSong extends RecyclerView.ViewHolder {
        public TextView songName;
        public TextView artistName;

        public ImageView singerImage;

        public ImageButton likeButton;
        public ImageButton addPlaylistButton;
        public ImageButton addToMeButton;


        public ViewHolderBePopSong(View itemView) {
            super(itemView);
            this.songName = (TextView) itemView.findViewById(R.id.songName);
            this.artistName = (TextView) itemView.findViewById(R.id.artistName);
            this.singerImage = (ImageView) itemView.findViewById(R.id.singerImage);
            this.likeButton = (ImageButton) itemView.findViewById(R.id.likeButton);
            this.addPlaylistButton = (ImageButton) itemView.findViewById(R.id.addPlaylistButton);
            this.addToMeButton = (ImageButton) itemView.findViewById(R.id.addToMeButton);
        }

    }

    public BePopAdapter(Context context, ArrayList<Song> mData) {
        this.mContext = context;
        this.mData = mData;
        this.initViewType();
    }

    private void initViewType() {
        mViewDataType = new ArrayList<>();
        for (Song song : mData) {
            if (!song.imageURL.equals("") && isGood(song.imageURL))
                mViewDataType.add(VIEW_TYPE.IMAGE.ordinal());
            else
                mViewDataType.add(VIEW_TYPE.NO_IMAGE.ordinal());
        }
    }

    private boolean isGood(String im) {
        return true;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_TYPE.IMAGE.ordinal()) {
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended, parent, false);
            vh = new ViewHolderBePopSong(v);
        }  else /*if (viewType == ViewType.NO_IMAGE.ordinal()) */{
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_no_image, parent, false);
            vh = new ViewHolderBePopSongNoImage(v);
        }
        return vh;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Song song = mData.get(position);

        int viewType = holder.getItemViewType();

        if (viewType == VIEW_TYPE.IMAGE.ordinal()) {
            ViewHolderBePopSong viewHolder = (ViewHolderBePopSong) holder;
            viewHolder.artistName.setText(song.artistName);
            viewHolder.songName.setText(song.songName);
            Picasso.with(mContext)
                    .load(song.imageURL)
                    .error(R.drawable.error_image)
                    .placeholder(R.drawable.error_image)
                    .into(viewHolder.singerImage);
        } else if (viewType == VIEW_TYPE.NO_IMAGE.ordinal()) {
            ViewHolderBePopSongNoImage viewHolder = (ViewHolderBePopSongNoImage) holder;
            viewHolder.artistName.setText(song.artistName);
            viewHolder.songName.setText(song.songName);
        }


    }

    @Override
    public int getItemViewType(int position) {
        return mViewDataType.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
