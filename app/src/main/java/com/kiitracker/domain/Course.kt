package com.kiitracker.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Course {
    abstract val campus: Campus
    abstract val timeSlot: TimeSlot
    abstract val fullName: String

    @Serializable
    class DE_LA(
        override val campus: Campus,
        override val timeSlot: TimeSlot
    ) : Course() {
        override fun toString() = "DE&LA"
        override val fullName = "Differential Equation & Linear Algebra"
    }

    @Serializable
    class Phy(
        override val campus: Campus,
        override val timeSlot: TimeSlot
    ) : Course() {
        override fun toString() = "Phy."
        override val fullName = "Physics"
    }

    @Serializable
    class EVS(
        override val campus: Campus,
        override val timeSlot: TimeSlot
    ) : Course() {
        override fun toString() = "EVS"
        override val fullName = "Environmental Science"
    }

    @Serializable
    class ScLs(
        override val campus: Campus,
        override val timeSlot: TimeSlot
    ) : Course() {
        override fun toString() = "Sc. LS"
        override val fullName = "Science of Living Systems"
    }

    @Serializable
    sealed class Lab(
        override val campus: Campus,
        override val timeSlot: TimeSlot,
        // override val classroom: String = "" // TODO: GET ALL LABS CLASSROOMS DATA FROM
    ) : Course() {

        @Serializable
        data class PhyLab(
            @SerialName("edLabTimeSlot")
            override val timeSlot: TimeSlot
        ) : Lab(Campus.Campus3, timeSlot) {
            override fun toString() = "Phy. Lab"
            override val fullName = "Physics Lab"
        }

        @Serializable
        data class ED_GRAPHICS(
            @SerialName("phyLabTimeSlot")
            override val timeSlot: TimeSlot
        ) : Lab(Campus.Campus3, timeSlot) {
            override fun toString() = "ED & Graphics"
            override val fullName = "Engineering Drawing & Graphics"
        }

        @Serializable
        data class PL_T_Programming(
            @SerialName("cLabCampus")
            override val campus: Campus,
            @SerialName("cLabTimeSlot")
            override val timeSlot: TimeSlot
        ) : Lab(campus, timeSlot) {
            override fun toString() = "PL(T) & Prog. Lab"
            override val fullName = "Tutorial & Programming Lab"
        }
    }

    @Serializable
    sealed class Elective(
        override val campus: Campus,
        override val timeSlot: TimeSlot,
        open val classroom: String
    ): Course() {
        @Serializable
        data class Nano(
            @SerialName("nanoCampus")
            override val campus: Campus,
            @SerialName("nanoTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("nanoClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "Nano"
            override val fullName = "Nanoscience"
        }
        @Serializable
        data class SM(
            @SerialName("smCampus")
            override val campus: Campus,
            @SerialName("smTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("smClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "SM"
            override val fullName = "Smart Materials"
        }
        @Serializable
        data class MD(
            @SerialName("mdCampus")
            override val campus: Campus,
            @SerialName("mdTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("mdClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "MD"
            override val fullName = "Molecular Diagnostics"
        }
        @Serializable
        data class SPH(
            @SerialName("sphCampus")
            override val campus: Campus,
            @SerialName("sphTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("sphClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "SPH"
            override val fullName = "Science of Public Health"
        }
        @Serializable
        data class OT(
            @SerialName("otCampus")
            override val campus: Campus,
            @SerialName("otTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("otClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "OT"
            override val fullName = "Optimization Techniques"
        }
        @Serializable
        data class BCE(
            @SerialName("bceCampus")
            override val campus: Campus,
            @SerialName("bceTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("bceClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "BCE"
            override val fullName = "Basic Civil Engineering"
        }
        @Serializable
        data class BME(
            @SerialName("bmeCampus")
            override val campus: Campus,
            @SerialName("bmeTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("bmeClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "BME"
            override val fullName = "Basic Mechanical Engineering"
        }
        @Serializable
        data class EML(
            @SerialName("emlCampus")
            override val campus: Campus,
            @SerialName("emlTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("emlClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "EML"
            override val fullName = "Elements of Machine Learning"
        }
        @Serializable
        data class BioM(
            @SerialName("biomCampus")
            override val campus: Campus,
            @SerialName("biomTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("biomClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "BioM"
            override val fullName = "Biomedical Engineering"
        }
        @Serializable
        data class BI(
            @SerialName("biCampus")
            override val campus: Campus,
            @SerialName("biTimeSlot")
            override val timeSlot: TimeSlot,
            @SerialName("biClassRoom")
            override val classroom: String
        ) : Elective(campus, timeSlot, classroom) {
            override fun toString() = "BI"
            override val fullName = "Basic Instrumentation"
        }
    }
}

/*
enum class Course {
    DE_LA,
    Phy,
    EVS,
    ScLs,
    PhyLab,
    ED_GRAPHICS,
    PL_T_Programming;
    enum class Electives {
        Nano,
        SM,
        MD,
        SPH,
        OT,
        BCE,
        BME,
        EML,
        BioM,
        BI
    }

}
@Serializable
sealed interface BaseCourseData {
    val campus: Campus
    val timeSlot: TimeSlot
}
@Serializable
data class CourseData(
    val course: Course,
    override val campus: Campus,
    override val timeSlot: TimeSlot
) : BaseCourseData
@Serializable
data class ElectiveCourseData(
    val course: Course.Electives,
    override val campus: Campus,
    override val timeSlot: TimeSlot,
    val classroom: String
) : BaseCourseData*/
