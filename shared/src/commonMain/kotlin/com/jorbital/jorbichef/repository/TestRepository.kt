package com.jorbital.jorbichef.repository

import com.jorbital.jorbichef.Database
import com.jorbital.jorbichef.api.FirestoreApi
import com.jorbital.jorbichef.models.Ingredient
import com.jorbital.jorbichef.models.firestore.TagDocument

class TestRepository(private val db: Database, private val api: FirestoreApi) {

    suspend fun syncFromApi() {
        val apiTags = api.getTags()
        apiTags.forEach {
            when (it) {
                is TagDocument.Default -> db.tagsQueries.insertOrReplace(
                    id = it.id,
                    name = it.name,
                    resourceId = it.resourceId
                )

                is TagDocument.Custom -> db.tagsQueries.insertOrReplace(
                    id = it.id,
                    name = it.name,
                    resourceId = null
                )
            }
        }
    }

    suspend fun getTags() = db.tagsQueries.selectAll().executeAsList()
}