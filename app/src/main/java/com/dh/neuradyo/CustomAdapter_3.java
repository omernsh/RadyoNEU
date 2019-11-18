package com.dh.neuradyo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;



public class CustomAdapter_3 extends ArrayAdapter<Message_3> {

    private FirebaseUser firebaseUser;

    public CustomAdapter_3(Context context, ArrayList<Message_3> chatList, FirebaseUser firebaseUser) {
        super(context, 0, chatList);
        this.firebaseUser = firebaseUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message_3 message3 = getItem(position);
        if (firebaseUser.getEmail().equalsIgnoreCase(message3.getUseremail())){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.right_item_layout_3, parent, false);

            TextView txtUser = (TextView) convertView.findViewById(R.id.txtUserRight);
            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageRight);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeRight);

            txtUser.setText(message3.getUsername());
            txtMessage.setText(message3.getUsermessage());
            txtTime.setText(message3.getUsermessagetime());

        }else{

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.left_item_layout_3, parent, false);

            TextView txtUser = (TextView) convertView.findViewById(R.id.txtUserLeft);
            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageLeft);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeLeft);

            txtUser.setText(message3.getUsername());
            txtMessage.setText(message3.getUsermessage());
            txtTime.setText(message3.getUsermessagetime());

        }

        return convertView;
    }
}
