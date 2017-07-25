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

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import io.realm.Realm;
import me.kaangenc.ktk.data.GameObject;

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

        FloatingActionButton addLink = (FloatingActionButton) findViewById(R.id.add_link);
        addLink.setImageDrawable(MaterialDrawableBuilder.with(this)
                .setIcon(MaterialDrawableBuilder.IconValue.LINK_VARIANT)
                .setColorResource(R.color.text_light)
                .build()
        );
        FloatingActionButton addNote = (FloatingActionButton) findViewById(R.id.add_note);
        addNote.setImageDrawable(MaterialDrawableBuilder.with(this)
                .setIcon(MaterialDrawableBuilder.IconValue.NOTE_PLUS)
                .setColorResource(R.color.text_light)
                .build()
        );
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        realm.close();
        realm = null;
    }

    public void addNote(View view) {
    }

    public void addLink(View view) {
    }
}
