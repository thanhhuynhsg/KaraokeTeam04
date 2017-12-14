package com.example.team04_final_project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.team04_final_project.R;
import com.example.team04_final_project.data.ReviewAndComment;

import java.util.List;

/**
 * Created by thand on 13/12/2017.
 */

public class ReviewAndCommentAdapter extends RecyclerView.Adapter<ReviewAndCommentAdapter.ViewHolder>{
    private List<ReviewAndComment> reviewAndCommentList;
    private Context context;
    public List<ReviewAndComment> getReviewAndCommentList() {
        return reviewAndCommentList;
    }

    public void setReviewAndCommentList(List<ReviewAndComment> reviewAndCommentList) {
        this.reviewAndCommentList = reviewAndCommentList;
    }

    public ReviewAndCommentAdapter(List<ReviewAndComment> reviewAndCommentList, Context context) {
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
        ReviewAndComment reviewAndComment = reviewAndCommentList.get(position);
        holder.ivAvatar.setImageResource(reviewAndComment.getmAvatar());
        holder.tvContentRAC.setText(reviewAndComment.getmName());
        holder.rbReviewAndComment.setRating(reviewAndComment.getmRateing());
        holder.tvContentRAC.setText(reviewAndComment.getmContent());
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
