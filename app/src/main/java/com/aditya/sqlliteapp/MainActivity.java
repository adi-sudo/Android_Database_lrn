package com.aditya.sqlliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper  DatabaseHelper;
    EditText editName,editSurname,editMarks,editid;
    Button btnAddData;
    Button btnviewAll;
    Button btnUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper  = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_marks);
        editid = (EditText) findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.btn_add_Data);
        btnviewAll = (Button) findViewById(R.id.btn_view);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete =(Button) findViewById(R.id.btn_delete);

        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

    public void DeleteData()    {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows= DatabaseHelper.deleteData(editid.getText().toString());
                if (deletedRows != 0)
                    Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"No Data Deleted",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void UpdateData()    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated= DatabaseHelper.updateData(editid.getText().toString(), editName.getText().toString(),
                        editSurname.getText().toString(), editMarks.getText().toString());
                if (isUpdated)
                    Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"No Data Update",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void AddData()   {
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              boolean isInserted=  DatabaseHelper.insertData(editName.getText().toString(),editSurname.getText().toString(),editMarks.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this,"No Data Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAll()   {
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = DatabaseHelper.getAllData();
                if (result.getCount() == 0) {
                    //show message
                    showMessage("Error","No Data Found");
                    return;
                }

                StringBuffer buffer= new StringBuffer();
                while (result.moveToNext()) {
                    buffer.append("Id :" + result.getString(0) + "\n");
                    buffer.append("Name :" + result.getString(1) + "\n");
                    buffer.append("Surname :" + result.getString(2) + "\n");
                    buffer.append("Marks :" + result.getString(3) + "\n\n");
                }
                //Show Message
                showMessage("Data", buffer.toString());
            }
        });
    }

    public void showMessage(String title,String Message)    {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


}