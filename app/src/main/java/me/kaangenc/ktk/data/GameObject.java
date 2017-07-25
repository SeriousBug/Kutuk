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

package me.kaangenc.ktk.data;


import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class GameObject extends RealmObject implements Named {
    @Required @PrimaryKey private String id;
    @Required private String name;
    private Category category;

    private RealmList<Link> links;

    @LinkingObjects("associatedObject")
    private final RealmResults<Note> notes = null;

    public GameObject() {}

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public RealmList<Link> getLinks() {
        return links;
    }

    public void setLinks(RealmList<Link> links) {
        this.links = links;
    }

    @Override
    public String getId() {
        return id;
    }

    public RealmResults<Note> getNotes() {
        return notes;
    }

    static public class RealmFactory implements NamedRealmFactory {
        Category category;

        public RealmFactory(Category category) {
            this.category = category;
        }

        @Override public Named create(Realm realm) {
            GameObject object = realm.createObject(GameObject.class, UUID.randomUUID().toString());
            object.setCategory(category);
            return object;
        }
    }
}
