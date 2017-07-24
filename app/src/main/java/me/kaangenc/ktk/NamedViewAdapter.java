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

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmObject;
import io.realm.RealmRecyclerViewAdapter;
import me.kaangenc.ktk.data.Named;

class NamedViewAdapter<D extends RealmObject & Named> extends RealmRecyclerViewAdapter<D, NamedViewAdapter.ViewHolder> {
    public static final String INTENT_ID_KEY = "NamedViewAdapterId";
    private ComponentName targetActivity;

    /**
     * @param data The query whose results will be displayed in this view.
     * @param targetActivity The activity that should be switched to when one of the results is clicked.
     *                       The activity will be passed the id of the element as an extra on the intent,
     *                       using {@link #INTENT_ID_KEY INTENT_ID_KEY} as a key.
     */
    NamedViewAdapter(@Nullable OrderedRealmCollection<D> data, ComponentName targetActivity) {
        super(data, true);
        setHasStableIds(true);
        this.targetActivity = targetActivity;
    }

    @Override
    public NamedViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.named_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NamedViewAdapter.ViewHolder holder, int position) {
        final Named named = getItem(position);
        assert named != null;
        holder.name.setText(named.getName());
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent()
                        .setComponent(targetActivity)
                        .putExtra(INTENT_ID_KEY, named.getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
        }
    }
}
