package c.sakshi.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {

    EditText textView;
    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //get edittext view
        textView = (EditText) findViewById(R.id.editText5);
        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if (noteid != -1) {
            //display content of note by retreiving "notes" arraylist in second activity
            Note note = Main2Activity.notes.get(noteid);
            String noteContent = note.getContent();

            //display contents of this note on screen
            textView.setText(noteContent);
        }
    }

    public void onClick(View view) {
        //get edittext view and content
        String content = textView.getText().toString();
        //initialize db instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        //initialize db helper class
        DBHelper db = new DBHelper(sqLiteDatabase);
        //set username by fetching it from sharedpreferences

        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(usernameKey,"");

        String username = str;
        //save information to db
        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if(noteid==-1){//Add note.
            title = "NOTE_" + (Main2Activity.notes.size()+1);
            db.saveNotes(username,title,content,date);
        }else{//update note.
            title = "NOTE_" +(noteid + 1);
            db.updateNote(title,date,content,username);
        }
        //go to second activity



        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("message",username);
        startActivity(intent);
    }
}
