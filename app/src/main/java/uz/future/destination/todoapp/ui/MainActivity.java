package uz.future.destination.todoapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import uz.future.destination.todoapp.R;
import uz.future.destination.todoapp.adapter.NoteAdapter;
import uz.future.destination.todoapp.data.db.entities.Note;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RecyclerView recyclerView;
    private FloatingActionButton add_btn;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter adapter = new NoteAdapter();

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setNotes(notes);
            }
        });


        add_btn = findViewById(R.id.noteAdd);
        add_btn.setOnClickListener(v -> {
            setAlertDialog();
        });




        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);




    }

    private void setAlertDialog() {
        builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.add_note,null);
        builder.setView(view);
        builder.setCancelable(true);
        EditText title = view.findViewById(R.id.add_title);
        EditText description = view.findViewById(R.id.add_description);
        Button save = view.findViewById(R.id.save);
        Button cancel = view.findViewById(R.id.cancel);
        NumberPicker priority = view.findViewById(R.id.add_priority);
        priority.setMaxValue(10);
        priority.setMinValue(1);
        int value = priority.getValue();
         dialog = builder.create();
         dialog.show();


         cancel.setOnClickListener(v -> {
             dialog.dismiss();
         });

         save.setOnClickListener(v -> {

             String titleString =title.getText().toString();
             String desString =description.getText().toString();

             if (titleString.isEmpty() || desString.isEmpty()){
                 Toast.makeText(this, "Tulle  all fields", Toast.LENGTH_SHORT).show();
             }
             else {
                 Note note = new Note(titleString,desString,value);
                 viewModel.insert(note);
                 dialog.dismiss();
             }
         });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.deleteAll){

            viewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
}