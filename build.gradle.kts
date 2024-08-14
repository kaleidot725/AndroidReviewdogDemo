import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.android.library) apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
}

subprojects {
    // Ktlint
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("1.3.1")
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
            reporter(ReporterType.SARIF)
        }
        ignoreFailures.set(true)
        filter {
            exclude("**/generated/**")
            include("**/kotlin/**")
        }
    }
}

detekt {
    toolVersion = "1.23.6"
    buildUponDefaultConfig = true
    basePath = projectDir.toString()
    ignoreFailures = true
    parallel = true
    autoCorrect = false
}

tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        sarif.required.set(true)
    }
}