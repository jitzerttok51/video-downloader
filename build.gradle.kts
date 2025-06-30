import org.example.tasks.CreateRelease

val token: String = project.property("github.token") as String

val prerelease: Boolean = "true".equals(project.property("github.prerelease") as String)

val appVersion = "${project.properties["build.major"]}.${project.properties["build.minor"]}.${project.properties["build.number"]}"

subprojects {
    version = appVersion
}

tasks.register<CreateRelease>("createRelease") {
    version.set(appVersion)
    accessToken.set(token)
    preRelease.set(prerelease)
}
