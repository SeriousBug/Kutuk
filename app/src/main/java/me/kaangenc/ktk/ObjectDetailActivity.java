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
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialDrawableBuilder.IconValue;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import me.kaangenc.ktk.data.GameObject;
import me.kaangenc.ktk.data.Note;

public class ObjectDetailActivity extends AppCompatActivity {
    Realm realm;
    GameObject object;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.object_detail);
        realm = Realm.getDefaultInstance();

        String id = getIntent().getStringExtra(NamedViewAdapter.INTENT_ID_KEY);
        object = realm.where(GameObject.class).equalTo("id", id).findFirst();

        setTitle(String.format("%s - %s",
                object.getCategory().getName(),
                object.getName())
        );

        final Context context = this;
        setupFloatingButton(R.id.add_link, IconValue.LINK_VARIANT, new View.OnClickListener() {
            @Override public void onClick(View view) {
            }
        });
        setupFloatingButton(R.id.add_note, IconValue.NOTE_PLUS, new View.OnClickListener() {
            @Override public void onClick(View view) {
                final Intent intent = new Intent(context, EditNoteActivity.class);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override public void execute(Realm realm) {
                        Note note = realm.createObject(Note.class, UUID.randomUUID().toString());
                        note.setAssociatedObject(object);
                        intent.putExtra("id", note.getId());
                    }
                });
                startActivity(intent);
            }
        });

        setupRecyclerView(R.id.notes, new NoteAdapter(object.getNotes()));
        setupRecyclerView(R.id.links, new LinkAdapter(object));
    }

    private void setupFloatingButton(@IdRes int id, IconValue value, View.OnClickListener listener) {
        FloatingActionButton button = (FloatingActionButton) findViewById(id);
        button.setImageDrawable(
                MaterialDrawableBuilder.with(this)
                        .setIcon(value)
                        .setColorResource(R.color.text_light)
                        .build()
        );
        button.setOnClickListener(listener);
    }

    private void setupRecyclerView(@IdRes int id, RealmRecyclerViewAdapter adapter) {
        RecyclerView view = (RecyclerView) findViewById(id);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(adapter);
        view.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        realm.close();
        realm = null;
    }
}
