package com.carwale.aepl.assignment5;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by aepl on 27/6/16.
 */
public class TitlesFragment extends ListFragment{
    private boolean dualPane;
    private int currentCheckIndex = 0;
    private int currentShowingindex = -1;
    private ArrayList<State> states;
    private int layoutId;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //dualPane = bundle.getBoolean("dual");
        View detailsFrame = getActivity().findViewById(R.id.details);
        dualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
        layoutId = android.R.layout.simple_list_item_activated_1;


        if(savedInstanceState != null){
            currentCheckIndex = savedInstanceState.getInt("curCheck", 0);
            currentShowingindex = savedInstanceState.getInt("curshow", -1);
            states = (ArrayList<State>) savedInstanceState.getSerializable("data");
        }
        else{
            Bundle bundle = getArguments();
            states = (ArrayList<State>) bundle.getSerializable("States");

        }

        ArrayList<String> stateNames= new ArrayList<>();
        for (State state : states){
            stateNames.add(state.stateName);
        }
        setListAdapter(new ArrayAdapter<String>(getActivity(), layoutId, stateNames));

        if(dualPane){
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showDetails(currentCheckIndex);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle){
        super.onSaveInstanceState(bundle);
        bundle.putInt("curCheck", currentCheckIndex);
        bundle.putSerializable("data", states);
        bundle.putInt("curShow", currentShowingindex);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int index, long id){
        showDetails(index);
    }

    private void showDetails(int index){
        currentCheckIndex = index;

        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if(dualPane){
            getListView().setItemChecked(index, true);

            if(currentCheckIndex != currentShowingindex){
                currentShowingindex = currentCheckIndex;
                DetailsFragment fragment = new DetailsFragment();
                Bundle bundle= new Bundle();
                bundle.putInt("index", currentCheckIndex);
                bundle.putSerializable("States", states);
                bundle.putBoolean("dualpane", dualPane);
                fragment.setArguments(bundle);
                transaction.replace(R.id.details, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }
        }
        else {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra("index", currentCheckIndex);
            intent.putExtra("States", states);
            intent.putExtra("dualpane", dualPane);
            startActivity(intent);
        }

    }
}
