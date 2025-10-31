plugins {
    java
    application
    kotlin("jvm") version "1.9.10"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("voxel.game.LwjglMain")
}

repositories {
    mavenCentral()
}

// Add LWJGL variables and dependencies for a lightweight 3D prototype
val lwjglVersion = "3.3.2"
val lwjglNatives = "natives-linux"

dependencies {
    implementation(kotlin("stdlib"))

    // LWJGL 3 (core + GLFW + OpenGL)
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl:lwjgl")
    implementation("org.lwjgl:lwjgl-glfw")
    implementation("org.lwjgl:lwjgl-opengl")

    runtimeOnly("org.lwjgl:lwjgl::${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-glfw::${lwjglNatives}")
    runtimeOnly("org.lwjgl:lwjgl-opengl::${lwjglNatives}")

    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "17"
}
