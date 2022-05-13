package com.example.a7minuteapp

data class ExerciseModel (
    val exerciseNo : Int,
    val imageId : Int,
    val name:String,
    var isCompleted: Boolean = false,
    var isSelected: Boolean = false
){
    companion object {
        val ExercisesList: ArrayList<ExerciseModel> = arrayListOf(
            ExerciseModel(1,R.drawable.ic_jumping_jacks_10,"Jumping Jacks"),
            ExerciseModel(2,R.drawable.ic_push_up_9,"Push Up"),
            ExerciseModel(3,R.drawable.ic_plank_8,"Plank"),
            ExerciseModel(4,R.drawable.ic_lunge_7,"Lunge"),
            ExerciseModel(5,R.drawable.ic_abdominal_crunch_5,"Abdominal Crunch"),
            ExerciseModel(6,R.drawable.ic_side_plank_1,"Side Plank"),
            ExerciseModel(7,R.drawable.ic_push_up_and_rotation_6,"Push Up Rotation"),
            ExerciseModel(8,R.drawable.ic_squat_2,"Squat"),
            ExerciseModel(9,R.drawable.ic_step_up_onto_chair_3,"Step Up Onto Chair"),
            ExerciseModel(10,R.drawable.ic_triceps_dip_on_chair_4,"Triceps Dip On Chair")
        )
    }
}