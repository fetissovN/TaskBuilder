package com.example.nick.taskbuilder.adapter;

import android.renderscript.RenderScript;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.controller.Controller;
import com.example.nick.taskbuilder.entity.Priority;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder> {
    private List<Task> listOfTasks = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Controller controller;
    private Priority priority;

    public CustomRecyclerAdapter(List<Task> listOfTasks, LayoutInflater layoutInflater, Controller controller) {
        this.listOfTasks = listOfTasks;
        this.layoutInflater = layoutInflater;
        this.controller = controller;
    }

    @Override
    public CustomRecyclerAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_task_recycler, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomRecyclerAdapter.CustomViewHolder holder, int position) {
        Task currentTask = listOfTasks.get(position);
        holder.name.setText(currentTask.getName());
//        holder.description.setText(currentTask.getDescription());
        holder.start.setText(currentTask.getStartes());
        holder.end.setText(currentTask.getExpires());
        if (currentTask.getPriority() != null){
            switch (currentTask.getPriority()){
                case "LOW":
                    holder.coloredCircle.setImageResource(R.drawable.green_drawable);
                    break;
                case "MEDIUM":
                    holder.coloredCircle.setImageResource(R.drawable.yellow_drawable);
                    break;
                case "HIGH":
                    holder.coloredCircle.setImageResource(R.drawable.orange_drawable);
                    break;
                case "VERY_HIGH":
                    holder.coloredCircle.setImageResource(R.drawable.red_drawable);
                    break;
            }
        }else {
            holder.coloredCircle.setImageResource(R.drawable.green_drawable);
        }

    }

    @Override
    public int getItemCount() {
        return listOfTasks.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CircleImageView coloredCircle;
        private TextView id;
        private TextView name;
        private TextView description;
        private TextView start;
        private TextView end;
        private ViewGroup container;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.coloredCircle = (CircleImageView) itemView.findViewById(R.id.circleItem);
            this.name = (TextView) itemView.findViewById(R.id.tv_item_name);
//            this.description = (TextView) itemView.findViewById(R.id.tv_item_description);
            this.start = (TextView) itemView.findViewById(R.id.tv_time_start);
            this.end = (TextView) itemView.findViewById(R.id.tv_time_expires);
            this.id = (TextView) itemView.findViewById(R.id.task_id);
            this.container = (ViewGroup) itemView.findViewById(R.id.parent_item);

            this.container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Task task = listOfTasks.get(
                    this.getAdapterPosition()
            );
            controller.onListItemClick(task);
        }
    }
}
