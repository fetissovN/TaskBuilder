package com.example.nick.taskbuilder.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.nick.taskbuilder.R;
import com.example.nick.taskbuilder.adapter.CustomRecyclerAdapter;
import com.example.nick.taskbuilder.adapter.RecyclerClickListener;
import com.example.nick.taskbuilder.controller.Controller;
import com.example.nick.taskbuilder.data.DB;
import com.example.nick.taskbuilder.entity.Task;
import com.example.nick.taskbuilder.entity.TaskDB;
import com.example.nick.taskbuilder.view.TaskActivity;
import com.example.nick.taskbuilder.view.ViewListTaskInterface;

import java.util.List;

public class CurrentWeekScheduleFragment extends Fragment implements ViewListTaskInterface{

    private LayoutInflater layoutInflater;
    private RecyclerView recyclerView;
    private CustomRecyclerAdapter adapter;

    private Controller controller;
    private List<Task> listOfData;

    public CurrentWeekScheduleFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_week_schedule, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rec_week_fragment);
        layoutInflater = getLayoutInflater();
        controller = new Controller(this, new DB(getContext()));

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setUpAdapterAndView(final List<Task> listOfTasks) {
        this.listOfData = listOfTasks;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerClickListener(getContext(), recyclerView ,new RecyclerClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        setUpAndOpenTaskActivity(listOfTasks.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }
                })
        );
        adapter = new CustomRecyclerAdapter(listOfTasks, layoutInflater, controller);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void deleteListItemAtPosition(int position) {
        listOfData.remove(position);
        adapter.notifyItemRemoved(position);
    }

    /*
    * Behaviour of clicking on item in recycler
    * */
    @Override
    public void setUpAndOpenTaskActivity(Task task) {
        Intent intent = new Intent(getContext(), TaskActivity.class);
        intent.putExtra("task", task);
        startActivity(intent);
    }

    private ItemTouchHelper.Callback createHelperCallback() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.RIGHT) {

            //not used, as the first parameter above is 0
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = viewHolder.getAdapterPosition();
                controller.onListItemSwiped(
                        position,
                        listOfData.get(position)
                );
            }

        };

        return simpleItemTouchCallback;
    }

//     class RecyclerClickListener implements RecyclerView.OnItemTouchListener {
//        public interface OnItemClickListener {
//            void onItemClick(View view, int position);
//
//            void onItemLongClick(View view, int position);
//        }
//
//        private OnItemClickListener mListener;
//
//        private GestureDetector mGestureDetector;
//
//        public RecyclerClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
//            mListener = listener;
//
//            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
//
//                    if (childView != null && mListener != null) {
//                        mListener.onItemLongClick(childView, recyclerView.getChildAdapterPosition(childView));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
//            View childView = view.findChildViewUnder(e.getX(), e.getY());
//
//            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
//                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
//            }
//
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//        }
//    }
}
