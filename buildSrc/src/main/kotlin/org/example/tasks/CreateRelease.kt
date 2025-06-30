package org.example.tasks

import okhttp3.Request
import okhttp3.RequestBody
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.io.IOException

abstract class CreateRelease : DefaultTask() {

    @get:Input
    abstract val version: Property<String>

    @get:Input
    abstract val accessToken: Property<String>

    @get:Input
    abstract val preRelease: Property<Boolean>

    @TaskAction
    fun execute() {
        createRelease(version.get(), accessToken.get())
    }

    private fun createRelease(version: String, token: String): Long {
        val body = JSONObject()
        println("v$version")
        body.put("tag_name", "v$version")
        body.put("target_commitish", "main")
        body.put("name", "v$version")
        body.put("draft", false)
        body.put("prerelease", preRelease.get())
        body.put("generate_release_notes", false)

        val request = Request.Builder()
            .url("https://api.github.com/repos/jitzerttok51/video-downloader/releases")
            .method("POST", RequestBody.create(JSON, body.toString()))
            .header("Authorization", "Bearer $token")
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .build()

        client.newCall(request).execute().use { response ->
            if(response.code != 201) {
                throw IOException("Cannot create release v$version ${response.code} ${response.body?.string()}")
            }

            return JSONObject(response.body!!.string()).getLong("id")
        }
    }
}