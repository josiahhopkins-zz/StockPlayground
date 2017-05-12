package com.example.josiah.stockplayground;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddGroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddGroupFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GroupAddListener mListener;

    public interface GroupAddListener {
        public void addGroup(String url);
    }

    public AddGroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddGroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddGroupFragment newInstance(String param1, String param2) {
        AddGroupFragment fragment = new AddGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_add_group, container, false);
        view.findViewById(R.id.add_new_group_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addGroup(buildGroupURL(view));
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GroupAddListener) {
            mListener = (GroupAddListener) context;
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



    private String buildGroupURL(View v){
        StringBuilder sb = new StringBuilder(StockActivity.GROUP_ADD_URL);
        try {
            String groupName = ((EditText) v.findViewById(R.id.group_add_name)).getText().toString();
            sb.append("groupName=");
            sb.append(groupName);
            String portfolio = ((EditText) v.findViewById(R.id.group_add_portfolio_value)).getText().toString();

            sb.append("&portfolioValue=");
            sb.append(URLEncoder.encode(portfolio, "UTF-8"));
            String owner = ((StockActivity) getActivity()).getUsername();
            sb.append("&owner=");
            sb.append(URLEncoder.encode(owner, "UTF-8"));
            Log.i("CourseAddFragment", sb.toString());
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return sb.toString();
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
