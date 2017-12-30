package com.example.team04_final_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.team04_final_project.R;
import com.example.team04_final_project.data.Rating_Comment;

import java.util.List;

/**
 * Created by thand on 13/12/2017.
 */

public class ReviewAndCommentAdapter extends RecyclerView.Adapter<ReviewAndCommentAdapter.ViewHolder>{
    private List<Rating_Comment> reviewAndCommentList;
    private Context context;
    public List<Rating_Comment> getReviewAndCommentList() {
        return reviewAndCommentList;
    }

    public void setReviewAndCommentList(List<Rating_Comment> reviewAndCommentList) {
        this.reviewAndCommentList = reviewAndCommentList;
    }

    public ReviewAndCommentAdapter(List<Rating_Comment> reviewAndCommentList, Context context) {
        this.reviewAndCommentList = reviewAndCommentList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review_and_comment, parent, false);
        return new ReviewAndCommentAdapter.ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Rating_Comment reviewAndComment = reviewAndCommentList.get(position);
        String img_url = reviewAndComment.getuAvata();
        Glide.with(context).load(img_url).into(holder.ivAvatar);
       // holder.ivAvatar.setImageResource(reviewAndComment.getmA());
        holder.tvNameRAC.setText(reviewAndComment.getuName());
        Float rating = Float.parseFloat(reviewAndComment.getuRating());
        holder.rbReviewAndComment.setRating(rating);
        holder.tvContentRAC.setText(reviewAndComment.getuComment());
    }

    @Override
    public int getItemCount() {
        return reviewAndCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvNameRAC, tvContentRAC;
        RatingBar rbReviewAndComment;
        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_Avatar);
            tvNameRAC = (TextView) itemView.findViewById(R.id.tv_NameRAC);
            tvContentRAC = (TextView) itemView.findViewById(R.id.tv_ContentRAC);
            rbReviewAndComment = (RatingBar) itemView.findViewById(R.id.rb_ReviewAndComment);
        }
    }
}
