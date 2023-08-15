package com.example.newagesafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText name,number;
    Button save,view;
    DatabaseHandler db;
    String numberpattern="^[6-9]\\d{9}$";
    //ListView listView;
    //Cursor c;
    SQLiteDatabase sqldb;
    //ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        view = findViewById(R.id.view);
        save = findViewById(R.id.save);
        //listView=findViewById(R.id.list_item);
        db = new DatabaseHandler(this);
        sqldb=db.getReadableDatabase();
        //c=db.getText();

       /* listAdapter=new ListAdapter(Register.this,R.layout.customize_adapter);
        listView.setAdapter(listAdapter);*/
        // for setting our customize adapter in list view

        //For Adding information
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String str_name = name.getText().toString();
                String str_number = number.getText().toString();
                if (!str_number.trim().matches(numberpattern)) {
                    number.setError("Invalid Number");
                    return;
                }
                //boolean savedata = db.addContacts(str_name, str_number);
                if (TextUtils.isEmpty(str_name) || TextUtils.isEmpty(str_number)) {
                    Toast.makeText(Register.this, "Add name and number", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    boolean savedata = db.addContacts(str_name, str_number);
                    if (savedata == true) {
                        Toast.makeText(Register.this, "Save Contact Details", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, "Contact already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                name.setText("");
                number.setText("");
            }
        });


        //For checking if there any information present in the row and displaying
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /*c=db.getText();
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
                }*/

                Intent intent=new Intent(Register.this,Display.class);
                Toast.makeText(Register.this, "Contact List", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });

        //for deleting
        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DataProvider dataProvider = (DataProvider) listAdapter.getItem(position);
                db.deleteContact(dataProvider.getName());
                listAdapter.getList().remove(position);
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);
                return true;
            }
        });*/
    }
}


/* view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor cur=db.getText();
                if(cur.getCount()==0)
                {
                    Toast.makeText(MainActivity.this,"Contacts not exists",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(cur.moveToNext())
                {
                    buffer.append("Name: "+cur.getString(0)+"\n");
                    buffer.append("Contact no.: "+cur.getString(1)+"\n\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Data");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }  */

/* String naam,num;
                        int id;
                        id=Integer.parseInt(c.getString(0));
                        naam=c.getString(1);
                        num=c.getString(2);
                        DataProvider provider=new DataProvider(id,naam,num);
                        lis*/
