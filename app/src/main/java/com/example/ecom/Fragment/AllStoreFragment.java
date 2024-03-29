package com.example.ecom.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecom.Model.Store;
import com.example.ecom.R;
import com.example.ecom.ViewHolder.StoreViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class AllStoreFragment extends Fragment {

    FirebaseAuth mAuth;

    RecyclerView recyclerViewStore;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;

    DatabaseReference productRef, storeRef;

    public AllStoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_store, container, false);

        mAuth = FirebaseAuth.getInstance();

        toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Stores");

        //DATABASE REFERENCE's
        productRef = FirebaseDatabase.getInstance().getReference().child("Products");
        storeRef = FirebaseDatabase.getInstance().getReference().child("Store");

        //STORE RECYCLER VIEW
        recyclerViewStore = (RecyclerView) view.findViewById(R.id.recyclerViewStore);
        recyclerViewStore.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewStore.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        startListening();
    }

    private void startListening() {
        Query query = FirebaseDatabase.getInstance().getReference().child("Store").limitToLast(50);
        FirebaseRecyclerOptions<Store> options = new FirebaseRecyclerOptions.Builder<Store>().setQuery(query, Store.class).build();
        FirebaseRecyclerAdapter<Store, StoreViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Store, StoreViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull StoreViewHolder storeViewHolder, final int i, @NonNull final Store store) {
                storeViewHolder.txtStoreName.setText("Shop Name: " + store.getShopNamee());
                String time = store.getFromTimee() + " to " + store.getToTimee();
                storeViewHolder.txtStoreTime.setText("Opening Time " + time);
                //storeViewHolder.txtProductPrice.setText(store.getPricee());
                Picasso.with(getActivity()).load(store.getImagee()).into(storeViewHolder.storeImage);

                storeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*Intent intent = new Intent(getActivity(), StoreMainActivity.class);
                        intent.putExtra("storeName", store.getShopNamee());
                        startActivity(intent);*/
                        StoreMainProfileFragment storeMainProfileFragment = new StoreMainProfileFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("storeName", store.getShopNamee());
                        storeMainProfileFragment.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.nav_host_fragment,storeMainProfileFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }

            @NonNull
            @Override
            public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_layout, parent, false);
                StoreViewHolder holder = new StoreViewHolder(view);
                return holder;
            }
        };
        recyclerViewStore.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

}
