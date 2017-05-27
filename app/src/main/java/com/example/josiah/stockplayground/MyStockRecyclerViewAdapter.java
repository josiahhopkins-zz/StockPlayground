package com.example.josiah.stockplayground;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.josiah.stockplayground.classes_a_user_has.Stock;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link } and makes a call to the
 * specified {@link StockListFragment.StockListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStockRecyclerViewAdapter extends RecyclerView.Adapter<MyStockRecyclerViewAdapter.ViewHolder> {

    private final List<Stock> mValues;
    private final StockListFragment.StockListFragmentInteractionListener mListener;

    public MyStockRecyclerViewAdapter(List<Stock> items, StockListFragment.StockListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_stock, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mStockNameView.setText(mValues.get(position).getName());
        holder.mStockTodayPrice.setText(Double.toString(mValues.get(position).getTodaysPrice()));
        holder.mStockTodayPrice.setText(Double.toString(mValues.get(position).getTodaysChange()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.ChooseStockToLookAt(holder.mItem);
                }
            }
        });
        Log.v("Number of Stocks", "" + this.getItemCount());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mStockNameView;
        public final TextView mStockTodayPrice;
        public final TextView mStockTodayChange;
        public Stock mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mStockNameView = (TextView) view.findViewById(R.id.stock_name);
            mStockTodayPrice = (TextView) view.findViewById(R.id.stock_today_price);
            mStockTodayChange = (TextView) view.findViewById(R.id.stock_today_change);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mStockTodayPrice.getText() + "'";
        }
    }
}
