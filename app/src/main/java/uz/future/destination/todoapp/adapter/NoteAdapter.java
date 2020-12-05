package uz.future.destination.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import uz.future.destination.todoapp.R;
import uz.future.destination.todoapp.data.db.entities.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private List<Note> notes = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.priority.setText(note.getPriority()+"");
        holder.description.setText(note.getDescription());
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    public Note getNote(int position){
        return  notes.get(position);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView priority;
        TextView title;
        TextView description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            priority = itemView.findViewById(R.id.tw_priority);
            title = itemView.findViewById(R.id.tw_title);
            description = itemView.findViewById(R.id.tw_description);
        }
    }
}
