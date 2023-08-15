package com.example.newagesafety;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Display extends AppCompatActivity {

    Cursor c;
    DatabaseHandler db;
    ListView listView;
    ListAdapter listAdapter;
    //SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        getSupportActionBar().setTitle("Your Contact List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView=findViewById(R.id.list_item);
        db = new DatabaseHandler(Display.this);
        //sqldb=db.getReadableDatabase();

        listAdapter=new ListAdapter(Display.this,R.layout.customize_adapter);
        listView.setAdapter(listAdapter);

        c=db.getText();
        if(c.moveToFirst())
        {
            //for getting information from cursor object
            do {
                String naam,num;

                naam=c.getString(0);
                num=c.getString(1);
                DataProvider provider=new DataProvider(naam,num);
                listAdapter.add(provider);

            }while(c.moveToNext());
        }

        //for deleting
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DataProvider dataProvider = (DataProvider) listAdapter.getItem(position);
                db.deleteContact(dataProvider.getName());
                listAdapter.getList().remove(position);
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);
                return true;
            }
        });

    }
}