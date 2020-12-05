package uz.future.destination.todoapp.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import uz.future.destination.todoapp.data.db.entities.Note;
import uz.future.destination.todoapp.data.repository.NoteRepository;

public class MainViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }
    public void insert(Note note){
        repository.insert(note);
    }
    public void update(Note note){
        repository.update(note);
    }
    public void delete(Note note){
        repository.delete(note);
    }
    public void deleteAll(){
        repository.deleteAll();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
