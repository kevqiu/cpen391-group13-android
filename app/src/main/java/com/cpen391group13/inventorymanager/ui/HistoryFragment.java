package com.cpen391group13.inventorymanager.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.R;
import com.cpen391group13.inventorymanager.api.models.Cycle;
import com.cpen391group13.inventorymanager.api.service.CycleClient;
import com.cpen391group13.inventorymanager.api.service.RetrofitService;
import com.cpen391group13.inventorymanager.ui.adapters.HistoryAdapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Fragment for the history view
 *
 * Adapted from https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */
public class HistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.history_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_history) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.history_progress_bar) ProgressBar historyProgressBar;

    private CycleClient client;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);

        getActivity().setTitle("History");

        historyProgressBar.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setOnRefreshListener(this);

        Retrofit retrofit = RetrofitService.getClient(this.getContext());
        client = retrofit.create(CycleClient.class);

        fetchCycles();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onRefresh() {
        fetchCycles();
    }

    private void fetchCycles() {
        Call<List<Cycle>> call = client.getCycles();
        call.enqueue(new Callback<List<Cycle>>() {
            @Override
            public void onResponse(Call<List<Cycle>> call, Response<List<Cycle>> response) {
                if (response.isSuccessful()) {
                    historyProgressBar.setVisibility(View.INVISIBLE);
                    List<Cycle> cycles = response.body();
                    if (cycles != null) {
                        Collections.sort(cycles, new Comparator<Cycle>() {
                            @Override
                            public int compare(Cycle c1, Cycle c2) {
                                return c1.getStartTime().compareTo(c2.getStartTime());
                            }
                        });
                        Collections.reverse(cycles);
                    }
                    recyclerView.setAdapter(new HistoryAdapter(getActivity(), cycles));
                } else {
                    Toast.makeText(getActivity(), "AHHHHHHHH :(", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cycle>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}
