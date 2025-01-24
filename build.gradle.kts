plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.2.1"
    id("org.jetbrains.changelog") version "1.3.1"
}

group = project.extra["pluginGroup"] as String
version = project.extra["pluginVersion"] as String

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()

    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    api("uk.com.robust-it:cloning:1.9.12")
    api("org.apache.commons:commons-text:1.11.0")
    testImplementation("junit:junit:4.13.2")
    intellijPlatform {
        local("/Users/xgblack/Applications/IntelliJ IDEA Ultimate 2025.1 EAP.app")
    }
}

//intellijPlatform {
//    pluginName.set(project.extra["pluginName"] as String)
//    version.set(project.extra["platformVersion"] as String)
//    type.set(project.extra["platformType"] as String)
//    downloadSources.set(true)
//    updateSinceUntilBuild.set(false)
//    buildSearchableOptions {
//        enabled.set(false)
//    }
//    // plugins.set(listOf("com.intellij.zh:203.392", "com.intellij.ja:203.392"))
//
//    runIde {
//        jvmArgs.set((project.extra["runIdeJvmArgs"] as String).split(','))
//    }
//
//    patchPluginXml {
//        sinceBuild.set(project.extra["pluginSinceBuild"] as String)
//        untilBuild.set(project.extra["pluginUntilBuild"] as String)
//        pluginDescription.set(file(project.extra["pluginDescription"] as String).readText(Charsets.UTF_8))
//        changeNotes.set(file(project.extra["pluginChangeNotes"] as String).readText(Charsets.UTF_8))
//    }
//}
intellijPlatform {
    pluginConfiguration {
        version = providers.gradleProperty("pluginVersion")

        // 使用 project.extra 中的 pluginDescription 属性
        //description = providers.fileContents(layout.projectDirectory.file("README.md")).asText.map {
        //    val start = "<!-- Plugin description -->"
        //    val end = "<!-- Plugin description end -->"
        //
        //    with(it.lines()) {
        //        if (!containsAll(listOf(start, end))) {
        //            throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
        //        }
        //        subList(indexOf(start) + 1, indexOf(end)).joinToString("\n").let(::markdownToHTML)
        //    }
        //}

        val changelog = project.changelog // local variable for configuration cache compatibility
        // Get the latest available change notes from the changelog file
        //changeNotes = providers.gradleProperty("pluginVersion").map { pluginVersion ->
        //    with(changelog) {
        //        renderItem(
        //            (getOrNull(pluginVersion) ?: getUnreleased())
        //                .withHeader(false)
        //                .withEmptySections(false),
        //            Changelog.OutputType.HTML,
        //        )
        //    }
        //}

        ideaVersion {
            sinceBuild = providers.gradleProperty("pluginSinceBuild")
            untilBuild = providers.gradleProperty("pluginUntilBuild")
        }
    }

    signing {
        certificateChain = providers.environmentVariable("CERTIFICATE_CHAIN")
        privateKey = providers.environmentVariable("PRIVATE_KEY")
        password = providers.environmentVariable("PRIVATE_KEY_PASSWORD")
    }

    publishing {
        token = providers.environmentVariable("PUBLISH_TOKEN")
        // The pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels = providers.gradleProperty("pluginVersion").map { listOf(it.substringAfter('-', "").substringBefore('.').ifEmpty { "default" }) }
    }

    pluginVerification {
        ides {
            recommended()
        }
    }
}


changelog {
    // Configure changelog if needed
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
} 