package uz.future.destination.todoapp.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uz.future.destination.todoapp.data.db.entities.Note;

@Database(entities = {Note.class},
        version = 1)
public abstract class NotesDatabase extends RoomDatabase {


    private static NotesDatabase instance;

    public abstract NoteDao noteDao();


    public static synchronized NotesDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext()
                    , NotesDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }


    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };


}
