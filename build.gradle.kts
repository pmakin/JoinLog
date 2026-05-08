import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.3.0"
    id("com.gradleup.shadow") version "9.4.0"
}

group = "me.animepdf"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    // PaperMC API
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.60-stable")

    // Kotlin
    compileOnly(kotlin("stdlib")) // downloaded by paper

    // SkinsRestorer
    compileOnly("net.skinsrestorer:skinsrestorer-api:15.12.0")

    // Configurate
    compileOnly("org.spongepowered:configurate-hocon:4.2.0") // downloaded by paper
    compileOnly("org.spongepowered:configurate-extra-kotlin:4.2.0") // downloaded by paper
}

val targetJavaVersion = 25
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        minimize()

        // Merge META-INF/services files where needed
        mergeServiceFiles()

        // Exclude signatures, maven/ and proguard/ from META-INF
        exclude("META-INF/*.SF")
        exclude("META-INF/*.DSA")
        exclude("META-INF/*.RSA")
        exclude("META-INF/maven/**")
        exclude("META-INF/proguard/**")

        // Exclude annotations
        exclude("org/jetbrains/annotations/**")
        exclude("org/intellij/lang/annotations/**")
    }

    build {
        dependsOn(shadowJar)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.compilerOptions {
    freeCompilerArgs.set(listOf("-Xannotation-default-target=param-property"))
}