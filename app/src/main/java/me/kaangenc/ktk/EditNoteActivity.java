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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import io.realm.Realm;
import me.kaangenc.ktk.data.Note;


public class EditNoteActivity extends AppCompatActivity {
    private Realm realm;
    private Note note;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note);
        realm = Realm.getDefaultInstance();

        String id = getIntent().getStringExtra("id");
        note = realm.where(Note.class).equalTo("id", id).findFirst();

        edit = (EditText) findViewById(R.id.contents);
        edit.setText(note.getContents());
    }

    public void saveOrCancel(final View view) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                if (view.getId() == R.id.save) {
                    note.setContents(edit.getText().toString());
                }
                if (note.isEmpty()) {
                    note.deleteFromRealm();
                }
            }
        });
        finish();
    }
}
