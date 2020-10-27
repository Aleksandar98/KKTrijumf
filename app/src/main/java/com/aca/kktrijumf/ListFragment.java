package com.aca.kktrijumf;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aca.kktrijumf.Adapters.CoachRecyAdapter;
import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment implements CoachRecyAdapter.ItemClickListener{

    String selectedGroup;
    private Callback callback;

    FirebaseFirestore db ;
     List<Coach> coachList;
     boolean backCliked = false;
    public ListFragment() {
        // Required empty public constructor
    }
    public ListFragment(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.coachesRecyclerView);
        FloatingActionButton fab = view.findViewById(R.id.addCoach);

         db = FirebaseFirestore.getInstance();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(),AddCoachActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        coachList = new ArrayList<>();
        final CoachRecyAdapter adapter = new CoachRecyAdapter(coachList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event

                MainFragment mf = new MainFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, mf).commit();

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        db.collection("Coaches")
                .whereArrayContains("groups",selectedGroup)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Coach c = document.toObject(Coach.class);
                                coachList.add(c);

                            }
                            adapter.setList(coachList);
                            adapter.notifyDataSetChanged();

                        } else {
                            Log.d("MojTag", "Error getting documents.", task.getException());
                        }
                    }
                });
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(view.getContext(),ListPlayerActivity.class);
        intent.putExtra("izabrani",coachList.get(position));
        intent.putExtra("izabranoGodiste",selectedGroup);
        startActivity(intent);

        //callback.exchangeFragment(position);
    }

    public interface Callback{
        public void exchangeFragment(int position);
    }
}