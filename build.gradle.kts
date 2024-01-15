plugins {
    alias(libs.plugins.jagr)
    alias(libs.plugins.algomate)
    alias(libs.plugins.style)
}

version = file("version").readLines().first()

configurations.all {
    resolutionStrategy {
        configurations.all {
            resolutionStrategy {
                force(
                      libs.algoutils.student,
                    libs.algoutils.tutor,
                    libs.junit.pioneer,
                )
            }
        }
    }
}
exercise {
    assignmentId.set("h10")
}

submission {
    // ACHTUNG!
    // Setzen Sie im folgenden Bereich Ihre TU-ID (NICHT Ihre Matrikelnummer!), Ihren Nachnamen und Ihren Vornamen
    // in Anführungszeichen (z.B. "ab12cdef" für Ihre TU-ID) ein!
    studentId = "ab12cdef"
    firstName = "sol_first"
    lastName = "sol_last"

    // Optionally require own tests for mainBuildSubmission task. Default is false
    requireTests = false
}

jagr {
    graders {
        val graderPublic by getting {
            graderName.set("H10-Public")
            rubricProviderName.set("h10.H10_RubricProviderPublic")
        }
        val graderPrivate by creating {
            parent(graderPublic)
            graderName.set("H10-Private")
            rubricProviderName.set("h10.H10_RubricProviderPrivate")
        }
    }
}
