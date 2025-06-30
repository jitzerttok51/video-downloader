package org.example.tasks

import okhttp3.Request
import okhttp3.RequestBody
import org.gradle.api.DefaultTask
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.file.RegularFile
import org.gradle.api.tasks.TaskAction
import org.json.JSONObject
import java.io.IOException
import java.nio.file.Path

abstract class UploadToRelease : DefaultTask() {

    @get:Input
    abstract val version: Property<String>

    @get:Input
    abstract val accessToken: Property<String>

    @get:InputFiles
    abstract val assets: ListProperty<Provider<RegularFile>>

    @TaskAction
    fun createRelease() {
        val id = getRelease(version.get(), accessToken.get())
        assets.get()
            .map { it.get().asFile.toPath() }
            .forEach { uploadAsset(id, it, accessToken.get()) }
    }

    private fun getRelease(version: String, token: String): Long {
        val request = Request.Builder()
            .url("https://api.github.com/repos/jitzerttok51/video-downloader/releases/tags/v$version")
            .header("Accept", "application/vnd.github+json")
            .header("X-GitHub-Api-Version", "2022-11-28")
            .build()

        client.newCall(request).execute().use { response ->
            if(response.code != 200) {
                throw IOException("Cannot create release v$version ${response.code} ${response.body?.string()}")
            }

            return JSONObject(response.body!!.string()).getLong("id")
        }
    }

    private fun uploadAsset(id: Long, path: Path, token: String): Long {
        val request = Request.Builder()
            .url("https://uploads.github.com/repos/jitzerttok51/video-downloader/releases/$id/assets?name=${path.fileName}")
            .method("POST", RequestBody.create(BINARY, path.toFile()))
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