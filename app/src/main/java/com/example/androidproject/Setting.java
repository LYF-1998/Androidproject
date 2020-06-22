package com.example.androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Setting extends Fragment {
    Button button1,button2,button3,button4;
    TextView username,number;
//    ArrayList<String> list = new ArrayList<>();
    UserInfo instance = UserInfo.getInstance();
//    List<String> list = instance.list;
//    FutureTask<List<String>> future;
//    public static String name = "test";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onStart() {
        super.onStart();
//        new Thread(future).start();

        button1 = getActivity().findViewById(R.id.orderbutton);
        button2 = getActivity().findViewById(R.id.addbutton);
        button3 = getActivity().findViewById(R.id.collectionbutton);
        button4 = getActivity().findViewById(R.id.serverbutton);
        username = getActivity().findViewById(R.id.username);
        number = getActivity().findViewById(R.id.number);
//        try {
//            list = future.get();
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
        List<String> list = instance.dishs;
        //Toast.makeText(this,String.valueOf(list == null),Toast.LENGTH_LONG).show();
        Log.i("+++++++++++++++++++",list.toString());
        username.setText(list.get(0));
        number.setText(list.get(1));
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(userInformationInterface.this,"button1",Toast.LENGTH_SHORT).show();
                //订单页面
                Intent intent = new Intent(getActivity(), K_BusinessOrder.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(userInformationInterface.this,"button2",Toast.LENGTH_SHORT).show();
                //地址页面
                Intent intent = new Intent(getActivity(), K_Address.class);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInformationInterface.this,"button3",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), K_AboutUs.class);
                startActivity(intent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(UserInformationInterface.this,"button4",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), K_Server.class);
                startActivity(intent);
            }
        });
    }

    public Setting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Setting.
     */
    // TODO: Rename and change types and number of parameters
    public static Setting newInstance(String param1, String param2) {
        Setting fragment = new Setting();
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
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }
}