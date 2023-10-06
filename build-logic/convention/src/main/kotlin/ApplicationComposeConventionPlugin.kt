import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class ApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.getByType<ApplicationExtension>().apply {
                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "1.4.0"
                }

                dependencies {
                    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
                    add("implementation", composeBom)

                    add("implementation", "androidx.compose.material3:material3")

                    add("implementation", "androidx.compose.ui:ui-tooling-preview")
                    add("debugImplementation", "androidx.compose.ui:ui-tooling")

                    add("implementation", "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

                    add("implementation", "androidx.navigation:navigation-compose:2.6.0")
                    add("implementation", "androidx.hilt:hilt-navigation-compose:1.0.0")

                    add("implementation", "androidx.paging:paging-compose:3.2.1")
                }

            }
        }

    }

}