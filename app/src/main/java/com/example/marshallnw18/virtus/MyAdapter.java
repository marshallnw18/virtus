package com.example.marshallnw18.virtus;

    import android.content.Context;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.TextView;

    import com.example.marshallnw18.virtus.supportingClasses.Exercise;

    import java.util.ArrayList;
    import java.util.List;

/**
 * Created by marshallnw18 on 2/22/2018.
 * Setup of Adapter found @https://medium.com/@dds861/how-to-create-recyclerview-and-cardview-in-android-7d859247a49e
 *
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    Context context;
    List<Exercise>exerciseList = new ArrayList<>();

    public MyAdapter(Context context, List<Exercise> exerciseList) {
        this.context = context;
        this.exerciseList = exerciseList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise exercise  = exerciseList.get(position);

        holder.mTextView.setText(exercise.getText1());
        holder.mTextView2.setText(exercise.getText2());
        holder.mImageView.setImageResource(exercise.getImage());
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        TextView mTextView2;
        public ViewHolder(View itemView) {
            super(itemView);
            this.mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.mTextView = (TextView) itemView.findViewById(R.id.textView);
            this.mTextView2 = (TextView) itemView.findViewById(R.id.textView2);
        }
    }
}
