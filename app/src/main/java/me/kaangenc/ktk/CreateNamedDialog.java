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
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import io.realm.Realm;
import me.kaangenc.ktk.data.Named;
import me.kaangenc.ktk.data.NamedRealmFactory;

class CreateNamedDialog {
    private AlertDialog dialog;
    private Context context;
    private Realm realm;
    private NamedRealmFactory factory;

    CreateNamedDialog(String title, Context context, NamedRealmFactory factory) {
        this.context = context;
        this.factory = factory;
        realm = Realm.getDefaultInstance();

        // Create the text entry portion of the dialog
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.create_named, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(String.format(context.getString(R.string.create_title), title))
                .setView(dialogView)
                // Disable button listeners to avoid the automatic dismiss behaviour
                .setPositiveButton(R.string.create_accept, null)
                .setNegativeButton(R.string.create_cancel, null);
        dialog = builder.create();
        // Set up custom listeners for the buttons
        dialog.setOnShowListener(new ButtonSetup());
    }

    void show() { dialog.show(); }

    private class ButtonSetup implements DialogInterface.OnShowListener {
        @Override public void onShow(DialogInterface dialogInterface) {
            Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

            positive.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    EditText nameField = (EditText) dialog.findViewById(R.id.name);
                    String name = nameField.getText().toString();

                    if (! name.isEmpty()) {
                        realm.beginTransaction();
                        Named named = factory.create(realm);
                        named.setName(name);
                        realm.commitTransaction();
                        dialog.dismiss();
                    } else {
                        nameField.setError(context.getString(R.string.create_error_empty));
                    }
                }
            });
            negative.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
    }
}
