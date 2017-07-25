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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Iterator;

import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import me.kaangenc.ktk.data.GameObject;
import me.kaangenc.ktk.data.Link;

class LinkAdapter extends RealmRecyclerViewAdapter<Link, LinkAdapter.ViewHolder> {
    private GameObject sourceObject;

    public LinkAdapter(GameObject sourceObject) {
        super(sourceObject.getLinks(), true);
        setHasStableIds(true);
        this.sourceObject = sourceObject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.link, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Context context = holder.description.getContext();
        final Link link = getItem(position);
        assert link != null;

        // Find the named of all objects that this link is connected to,
        // except the one we're looking at
        RealmResults<GameObject> targetObjects = link.getLinkedObjects().where()
                .notEqualTo("id", sourceObject.getId()).findAll();
        StringBuilder targetText = new StringBuilder();

        final String listSeparator = context.getString(R.string.list_separator);
        for (Iterator<GameObject> iterator = targetObjects.iterator() ; iterator.hasNext();) {
            GameObject object = iterator.next();
            targetText.append(object.getName());
            if (iterator.hasNext()) {
                targetText.append(listSeparator);
            }
        }

        String linkFormat = context.getString(R.string.link_text);
        holder.description.setText(String.format(
                linkFormat, link.getText(), sourceObject.getName(), targetText.toString()
        ));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView description;

        ViewHolder(View itemView) {
            super(itemView);
            description = (TextView) itemView.findViewById(R.id.link);
        }
    }
}
