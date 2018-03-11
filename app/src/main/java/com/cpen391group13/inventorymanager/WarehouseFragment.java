package com.cpen391group13.inventorymanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cpen391group13.inventorymanager.api.models.Warehouse;
import com.cpen391group13.inventorymanager.api.service.WarehouseClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WarehouseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WarehouseFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Adapted from https://developer.android.com/samples/RecyclerView/src/com.example.android.recyclerview/RecyclerViewFragment.html
 */
public class WarehouseFragment extends Fragment {
    //bind views
    @BindView(R.id.warehouse_recycler_view) RecyclerView recyclerView;

    private WarehouseAdapter warehouseAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public WarehouseFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WarehouseFragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static WarehouseFragment newInstance(String param1, String param2) {
        WarehouseFragment fragment = new WarehouseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_warehouse, container, false);
        ButterKnife.bind(this, view);

        /*Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.107:5000")
                .addConverterFactory(GsonConverterFactory.create());

        final Retrofit retrofit = builder.build();

        WarehouseClient client = retrofit.create(WarehouseClient.class);
        Call<List<Warehouse>> call = client.getWarehouses();

        call.enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                List<Warehouse> warehouses = response.body();

                warehouseAdapter = new WarehouseAdapter(getActivity(), warehouses);
                recyclerView.setAdapter(warehouseAdapter);
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), "error :(", Toast.LENGTH_SHORT).show();
            }
        });*/

        Warehouse warehouse1 = new Warehouse(1, "UBC", 49, -123);
        Warehouse warehouse2 = new Warehouse(2, "UofT", 43, -79);
        Warehouse warehouse3 = new Warehouse(3, "McGill", 45, -73);
        Warehouse warehouse4 = new Warehouse(4, "SFU", 49, -122);
        Warehouse warehouse5 = new Warehouse(5, "Western", 43, -81);
        Warehouse warehouse6 = new Warehouse(4, "Harvard", 42, -71);

        List<Warehouse> warehouses = new ArrayList<Warehouse>();
        warehouses.add(warehouse1);
        warehouses.add(warehouse2);
        warehouses.add(warehouse3);
        warehouses.add(warehouse4);
        warehouses.add(warehouse5);
        warehouses.add(warehouse6);


        warehouseAdapter = new WarehouseAdapter(getActivity(), warehouses);
        recyclerView.setAdapter(warehouseAdapter);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
