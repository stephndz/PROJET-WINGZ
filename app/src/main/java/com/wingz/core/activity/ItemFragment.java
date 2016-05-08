/*
 * Copyright (c) 2016. The Wingz Project
 * Developed by Wingz Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wingz.core.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wingz.core.model.Site;
import com.wingz.core.storage.SiteAccess;
import com.wingz.core.test.R;
import com.wingz.core.activity.dummy.DummyContent;
import com.wingz.core.activity.dummy.DummyContent.DummyItem;

import java.util.Iterator;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Site> toShow;
    protected List<Site> currentList;
    protected MyItemRecyclerViewAdapter mAdapter;
    private final String TAG = "ItemFragment";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    // TODO: Customize parameter initialization
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        SiteAccess siteAccess = SiteAccess.getInstance(this.getContext());
        siteAccess.open();
        toShow = siteAccess.getAll();
        siteAccess.close();
        currentList = toShow.subList(0,1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyItemRecyclerViewAdapter(currentList, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Checks if a new interesting point can be found 1000m near the current location
     * @param location new updated location
     * @return true if the content was updated
     */
    public boolean checkLocationForSite(Location location){
        Location siteLocation = new Location(location);
        Site item;
        // Message to display in the notification
        String message = "New content available";
        boolean areNewSiteDiscovered = false;
        if(toShow != null){
            int i = toShow.size() -1;
            while(i >= 0){
                //Log.d(TAG, Integer.toString(toShow.size()));
                item = toShow.get(i);
                siteLocation.setLatitude(item.getLatitude());
                siteLocation.setLongitude(item.getLongitude());
                if(location.distanceTo(siteLocation) < 1000){
                    currentList.add(item);
                    toShow.remove(i);
                    mAdapter.notifyDataSetChanged();
                    areNewSiteDiscovered = true;
                    message = item.getContent();
                }
                i--;
            }
            // Trigger a notification if a new Site is near
            if(areNewSiteDiscovered){
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("New Site discovered")
                                .setContentText(message);
                // Creates an explicit intent for an Activity in your app
                Intent resultIntent = new Intent(getActivity(), ItemFragment.class);

            }
            return areNewSiteDiscovered;
        }
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Site item);
        boolean onNewLocation(Location location);
    }
}
