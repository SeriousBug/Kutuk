/*
 * Copyright 2017 Kaan Genç
 *
 * This file is part of Kütük.
 *
 * Kütük is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kütük is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Kütük.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.kaangenc.ktk;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import me.kaangenc.ktk.data.Note;


class NoteAdapter extends RealmRecyclerViewAdapter<Note, NoteAdapter.ViewHolder> {
    public NoteAdapter(@Nullable OrderedRealmCollection<Note> data) {
        super(data, true); // Realm always auto-updates
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Note note = getItem(position);
        assert note != null;
        holder.contents.setText(note.getContents());
        holder.contents.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                final Context context = view.getContext();
                Intent edit = new Intent(context, EditNoteActivity.class);
                edit.putExtra("id", note.getId());
                context.startActivity(edit);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView contents;

        ViewHolder(View itemView) {
            super(itemView);
            contents = (TextView) itemView.findViewById(R.id.contents);
        }
    }
}
