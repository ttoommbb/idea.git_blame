import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.intellij") version "0.4.9"
    kotlin("jvm") version "1.3.31"
    id("ch.netzwerg.release") version "1.2.5"
}

group = "dev.gaozone"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(
        url = "https://oss.sonatype.org/content/repositories/snapshots/"
    )
    maven(
        url = "https://dl.bintray.com/jetbrains/intellij-plugin-service"
    )
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

var ideaCacheDir = File(System.getProperty("user.home")).resolve("intellij-gradle-cache").absolutePath
println("Idea cache dir: " + ideaCacheDir)
// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = property("ideaVersion") as String
    type = property("ideaType") as String
    updateSinceUntilBuild = false
    downloadSources = true
//    plugins = ["git4idea", "gr.jchrist.gitextender:0.4.1", "zielu.gittoolbox"]
    ideaDependencyCachePath = ideaCacheDir

    setPlugins("Groovy", "gradle", "Kotlin", "maven", "properties", "junit", "git4idea", "gr.jchrist.gitextender:0.4.1")
}
tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    changeNotes(
        """
      Add change notes here.<br>
      <em>most HTML tags may be used</em>"""
    )
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}